package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.model.dao.PaymentDao;
import ua.training.model.entity.Payment;
import ua.training.model.exception.ExpiredPaymentDataException;
import ua.training.model.exception.NotEnoughTicketsException;
import ua.training.model.util.DBQueryBundleManager;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ua.training.Constants.*;

public class JDBCPaymentDao implements PaymentDao {
    private static final Logger log = Logger.getLogger(JDBCPaymentDao.class);

    private static final String SQL_QUERY_SAVE_PAYMENT = "sql.query.save.payment";
    private static final String SQL_QUERY_SAVE_PAYMENT_UPDATE_TICKETS = "sql.query.save.payment.update.tickets";
    private static final String SQL_QUERY_FIND_USER_CART_TICKETS_WITH_REST_OF_NOT_SOLD_TICKETS = "find.user.cart.tickets.with.rest.of.not.sold.tickets";

    private static final String PAYMENT_SAVE_FAILED_NO_ROWS_AFFECTED = "Saving payment failed, no rows affected. User id: ";
    private static final String PAYMENT_SAVE_FAILED_NO_ID_OBTAINED = "Saving payment failed, no ID obtained. User id: ";
    private static final String PAYMENT_SAVE_FAILED_NOT_ALL_TICKETS_UPDATED = "Payment save failed not all tickets updated. User id: ";
    private static final String PAYMENT_SUM = ". Payment sum: ";
    private static final String PAYMENT_DATA_NOT_ACTUAL = "Payment data is not actual. Exhibit id :";
    private static final String TICKET_ID = ". Ticket id: ";
    private static final String WRONG_TICKET_SUM_OR_QUANTITY = "Payment. Wrong ticket sum or quantity. User id: ";
    private static final int ZERO_TICKET_QUANTITY = 0;

    private final Connection connection;

    public JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    private int save(Payment payment) throws Exception {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_SAVE_PAYMENT);
        String sqlQuery2 = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_SAVE_PAYMENT_UPDATE_TICKETS);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2)) {
            preparedStatement.setInt(1, payment.getTicketQuantity());
            preparedStatement.setLong(2, payment.getPaidSum());
            preparedStatement.setObject(3, payment.getPaidDate());
            preparedStatement.setLong(4, payment.getUserId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                log.error(MessageUtil.getRuntimeExceptionMessage(PAYMENT_SAVE_FAILED_NO_ROWS_AFFECTED + payment.getUserId()
                        + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity()));
                throw new RuntimeException(PAYMENT_SAVE_FAILED_NO_ROWS_AFFECTED + payment.getUserId()
                        + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
            }
            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    payment.setId(generatedKey.getLong(1));
                } else {
                    connection.rollback();
                    log.error(MessageUtil.getRuntimeExceptionMessage(PAYMENT_SAVE_FAILED_NO_ID_OBTAINED + payment.getUserId()
                            + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity()));
                    throw new RuntimeException(PAYMENT_SAVE_FAILED_NO_ID_OBTAINED + payment.getUserId()
                            + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
                }
            }

            preparedStatement2.setLong(1, payment.getId());
            preparedStatement2.setLong(2, payment.getUserId());
            affectedRows = preparedStatement2.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new Exception(PAYMENT_SAVE_FAILED_NOT_ALL_TICKETS_UPDATED + payment.getUserId()
                        + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
            }
            return affectedRows;
        }
    }

    @Override
    public void makePayment(Payment payment) throws Exception {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(
                SQL_QUERY_FIND_USER_CART_TICKETS_WITH_REST_OF_NOT_SOLD_TICKETS);
        ResultSet resultSet;
        long ticketSum = 0;
        int ticketQuantity = 0;
        int resultRecordsQuantity = 0;
        int tickedRecordAffected = 0;

        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, payment.getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultRecordsQuantity++;
                ticketQuantity += resultSet.getInt(FIELD_DB_TICKET_QUANTITY);
                ticketSum += resultSet.getLong(FIELD_DB_TICKET_SUM);
                isTicketExhibitDateCorrect(resultSet);

                if (resultSet.getInt(FIELD_DB_REMAIN_TICKETS) < ZERO_TICKET_QUANTITY) {
                    connection.rollback();
                    throw new NotEnoughTicketsException(PAYMENT_NOT_ENOUGH_TICKETS + payment.getUserId()
                            + SLASH_SYMBOL + resultSet.getInt(FIELD_DB_REMAIN_TICKETS));
                }
            }
        }
        if (ticketQuantity != payment.getTicketQuantity() || ticketSum != payment.getPaidSum()) {
            connection.rollback();
            throw new ExpiredPaymentDataException(WRONG_TICKET_SUM_OR_QUANTITY + payment.getUserId()
                    + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
        }

        tickedRecordAffected = save(payment);

        if (tickedRecordAffected != resultRecordsQuantity) {
            throw new ExpiredPaymentDataException(PAYMENT_SAVE_FAILED_NOT_ALL_TICKETS_UPDATED + payment.getUserId()
                    + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
        }

        connection.commit();
    }

    private void isTicketExhibitDateCorrect(ResultSet resultSet) throws SQLException, ExpiredPaymentDataException {
        LocalDate ticketExhibitDate = resultSet.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class);
        LocalDate exhibitStartDate = resultSet.getObject(FIELD_DB_START_DATE_TIME, LocalDateTime.class).toLocalDate();
        LocalDate exhibitEndDate = resultSet.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class).toLocalDate();
        LocalTime timeExhibitEnd = resultSet.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class).toLocalTime();
        if (!CheckUtils.isExhibitDateTimeActual(ticketExhibitDate, exhibitStartDate, exhibitEndDate, timeExhibitEnd)) {
            connection.rollback();
            throw new ExpiredPaymentDataException(PAYMENT_DATA_NOT_ACTUAL + resultSet.getLong(FIELD_DB_EXHIBIT_ID)
                    + TICKET_ID + resultSet.getLong(FIELD_DB_ID));
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(DB_CONNECTION_CLOSING_ERROR);
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }
}
