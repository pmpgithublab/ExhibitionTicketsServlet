package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.util.CheckUtils;
import ua.training.model.dao.PaymentDao;
import ua.training.model.entity.Payment;
import ua.training.model.exception.ExpiredPaymentDataException;
import ua.training.model.exception.NotEnoughTicketsException;
import ua.training.model.util.DBQueryBundleManager;
import ua.training.util.MessageUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ua.training.Constants.*;

public class JDBCPaymentDao implements PaymentDao {
    private static final Logger log = Logger.getLogger(JDBCPaymentDao.class);

    private static final String SQL_QUERY_SAVE_PAYMENT = "sql.query.save.payment";
    private static final String SQL_QUERY_SAVE_PAYMENT_UPDATE_TICKETS = "sql.query.save.payment.update.tickets";
    private static final String SQL_QUERY_FIND_TICKET_BY_USER_ID_WITH_EXHIBIT_DATES_AND_NOT_PAID = "find.tickets.by.user.id.with.exhibit.dates.and.not.paid";
    private static final String SQL_QUERY_FIND_SOLD_TICKETS_QUANTITY_AND_MAX_VISITORS_QUANTITY_BY_EXHIBIT_ID = "find.sold.tickets.quantity.and.max.visitors.quantity.by.exhibit.id";

    private static final String PAYMENT_SAVE_FAILED_NO_ROWS_AFFECTED = "Saving payment failed, no rows affected. User id: ";
    private static final String PAYMENT_SAVE_FAILED_NO_ID_OBTAINED = "Saving payment failed, no ID obtained. User id: ";
    private static final String PAYMENT_SAVE_FAILED_NOT_ALL_TICKETS_UPDATED = "Payment save failed not all tickets updated. User id: ";
    private static final String PAYMENT_SUM = ". Payment sum: ";
    private static final String PAYMENT_DATA_NOT_ACTUAL = "Payment data is not actual. Exhibit id :";
    private static final String TICKET_ID = ". Ticket id: ";
    private static final String WRONG_TICKET_SUM_OR_QUANTITY = "Payment. Wrong ticket sum or quantity. User id: ";
    private static final String UNION_STRING = " union ";
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
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getLong(1));
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
                SQL_QUERY_FIND_TICKET_BY_USER_ID_WITH_EXHIBIT_DATES_AND_NOT_PAID);
        String sqlQuery2 = DBQueryBundleManager.INSTANCE.getProperty(
                SQL_QUERY_FIND_SOLD_TICKETS_QUANTITY_AND_MAX_VISITORS_QUANTITY_BY_EXHIBIT_ID);
        ResultSet resultSet;
        long ticketSum = 0;
        int ticketQuantity = 0;
        int resultRecordsQuantity = 0;
        int tickedRecordAffected = 0;
        List<String> exhibitIdAndDate = new ArrayList<>();

        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, payment.getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultRecordsQuantity++;
                ticketQuantity += resultSet.getInt(FIELD_DB_TICKET_QUANTITY);
                ticketSum += resultSet.getLong(FIELD_DB_TICKET_SUM);
                exhibitIdAndDate.add(resultSet.getLong(FIELD_DB_EXHIBIT_ID) + AMPERSAND_SIGN
                        + resultSet.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class));
                isTicketExhibitDateCorrect(resultSet);
            }
        }
        if (ticketQuantity != payment.getTicketQuantity() || ticketSum != payment.getPaidSum()) {
            connection.rollback();
            throw new ExpiredPaymentDataException(WRONG_TICKET_SUM_OR_QUANTITY + payment.getUserId()
                    + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
        }

        tickedRecordAffected = save(payment);

        isAllTicketsUpdated(payment, resultRecordsQuantity, tickedRecordAffected);

        try (PreparedStatement preparedStatement2 =
                     connection.prepareStatement(addUnionToQuery(sqlQuery2, exhibitIdAndDate))) {
            isEnoughFreeTickets(payment, exhibitIdAndDate, preparedStatement2);
        }
        connection.commit();
    }

    private void isTicketExhibitDateCorrect(ResultSet resultSet) throws SQLException, ExpiredPaymentDataException {
        LocalDate ticketExhibitDate;
        LocalDate exhibitStartDate;
        LocalDate exhibitEndDate;
        LocalTime timeExhibitEnd;
        ticketExhibitDate = resultSet.getObject(FIELD_DB_EXHIBIT_DATE, LocalDate.class);
        exhibitStartDate = resultSet.getObject(FIELD_DB_START_DATE_TIME, LocalDateTime.class).toLocalDate();
        exhibitEndDate = resultSet.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class).toLocalDate();
        timeExhibitEnd = resultSet.getObject(FIELD_DB_END_DATE_TIME, LocalDateTime.class).toLocalTime();
        if (!CheckUtils.isExhibitDateTimeActual(ticketExhibitDate, exhibitStartDate, exhibitEndDate, timeExhibitEnd)) {
            connection.rollback();
            throw new ExpiredPaymentDataException(PAYMENT_DATA_NOT_ACTUAL + resultSet.getLong(FIELD_DB_EXHIBIT_ID)
                    + TICKET_ID + resultSet.getLong(FIELD_DB_ID));
        }
    }

    private void isEnoughFreeTickets(Payment payment, List<String> exhibitIdAndDate, PreparedStatement preparedStatement2)
            throws SQLException, NotEnoughTicketsException {

        ResultSet resultSet;
        for (int i = 0; i < exhibitIdAndDate.size(); i++) {
            String key = exhibitIdAndDate.get(i);
            preparedStatement2.setObject((i * 2 + 1), LocalDate.parse(key.substring(key.indexOf(AMPERSAND_SIGN) + ONE_ELEMENT)));
            preparedStatement2.setLong((i * 2 + 2), Long.parseLong(key.substring(0, key.indexOf(AMPERSAND_SIGN))));
        }
        resultSet = preparedStatement2.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getInt(FIELD_DB_REMAIN_TICKETS) < ZERO_TICKET_QUANTITY) {
                connection.rollback();
                throw new NotEnoughTicketsException(PAYMENT_NOT_ENOUGH_TICKETS + payment.getUserId()
                        + SLASH_SYMBOL + resultSet.getInt(FIELD_DB_REMAIN_TICKETS));
            }
        }
    }

    private void isAllTicketsUpdated(Payment payment, int resultRecordsQuantity, int tickedRecordAffected) throws ExpiredPaymentDataException {
        if (tickedRecordAffected != resultRecordsQuantity) {
            throw new ExpiredPaymentDataException(PAYMENT_SAVE_FAILED_NOT_ALL_TICKETS_UPDATED + payment.getUserId()
                    + PAYMENT_SUM + payment.getPaidSum() + TICKET_QUANTITY + payment.getTicketQuantity());
        }
    }

    private String addUnionToQuery(String sqlQuery, List<String> exhibitIdAndDate) {
        if (exhibitIdAndDate.size() == ONE_ELEMENT) {
            return sqlQuery;
        }

        StringBuilder sb = new StringBuilder(sqlQuery);
        for (int i = 1; i < exhibitIdAndDate.size(); i++) {
            sb.append(UNION_STRING);
            sb.append(sqlQuery);
        }

        return sb.toString();
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
