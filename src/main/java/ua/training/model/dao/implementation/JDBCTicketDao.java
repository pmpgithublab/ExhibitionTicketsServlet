package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.model.exception.TicketDeleteException;
import ua.training.model.util.DBQueryBundleManager;
import ua.training.model.dao.TicketDao;
import ua.training.model.dao.mapper.*;
import ua.training.model.dto.AdminStatisticDTO;
import ua.training.model.dto.ReportDTO;
import ua.training.model.dto.TicketDTO;
import ua.training.model.dto.UserStatisticDTO;
import ua.training.model.entity.Ticket;
import ua.training.util.MessageUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.training.Constants.*;

public class JDBCTicketDao implements TicketDao {
    private static final Logger log = Logger.getLogger(JDBCTicketDao.class);

    private static final String SQL_QUERY_FIND_USER_STATISTIC_RECORD_QUANTITY = "sql.query.find.user.statistic.record.quantity";
    private static final String SQL_QUERY_FIND_USER_STATISTIC = "sql.query.find.user.statistic";
    private static final String SQL_QUERY_FIND_ADMIN_STATISTIC_RECORD_QUANTITY = "sql.query.find.admin.statistic.record.quantity";
    private static final String SQL_QUERY_FIND_ADMIN_STATISTIC = "sql.query.find.admin.statistic";
    private static final String SQL_QUERY_INSERT_NEW_TICKET = "insert.new.ticket";
    private static final String SQL_QUERY_UPDATE_TICKET = "update.ticket";
    private static final String SQL_QUERY_FIND_TICKET_BY_EXHIBIT_ID_AND_EXHIBIT_DATE_AND_USER_ID_AND_NOT_PAID = "find.ticket.by.exhibit.id.and.exhibit.date.and.user.id.and.not.paid";
    private static final String SQL_QUERY_FIND_TICKETS_BY_USER_ID_AND_NOT_PAID = "find.tickets.by.user.id.and.not.paid";
    private static final String SQL_QUERY_FIND_TICKETS_SUM_AND_QUANTITY_BY_USER_ID_AND_NOT_PAID = "find.tickets.sum.and.quantity.by.user.id.and.not.paid";
    private static final String SQL_QUERY_DELETE_ALL_NOT_PAID_TICKETS_FROM_CART = "delete.all.not.paid.tickets.from.cart";
    private static final String SQL_QUERY_DELETE_BY_ID_NOT_PAID_TICKET_FROM_CART = "delete.by.id.not.paid.tickets.from.cart";

    private static final String DB_TICKET_SAVED_ID = "DB ticket saved. Id: ";
    private static final String DB_TICKET_SAVING_ERROR = "DB ticked saving error. Name: ";
    private static final String DB_TICKETS_DELETED_FROM_CART = "Tickets deleted from cart";
    private static final String DB_TICKET_DELETED_FROM_CART_BY_ID = "Ticket deleted from cart by id";
    private static final String DB_ALL_TICKETS_DELETING_FROM_CART_ERROR = "All tickets deleting from cart error";
    private static final String DB_TICKET_DELETING_BY_ID_FROM_CART_ERROR = "Deleting ticket by id from cart error";

    private final Connection connection;


    public JDBCTicketDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Ticket ticket) throws Exception {
        String sqlQuery;
        if (ticket.getId() == null) {
            sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_INSERT_NEW_TICKET);
        } else {
            sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_UPDATE_TICKET);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            if (ticket.getId() == null) {
                preparedStatement.setObject(1, ticket.getExhibitDate());
                preparedStatement.setInt(2, ticket.getTicketQuantity());
                preparedStatement.setLong(3, ticket.getTicketSum());
                preparedStatement.setLong(4, ticket.getExhibitId());
                preparedStatement.setLong(5, ticket.getHallId());
                preparedStatement.setLong(6, ticket.getUserId());
            } else {
                preparedStatement.setInt(1, ticket.getTicketQuantity());
                preparedStatement.setLong(2, ticket.getTicketSum());
                preparedStatement.setLong(3, ticket.getId());
            }
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(DB_TICKET_SAVING_ERROR + ticket.getExhibitDate()
                        + SLASH_SYMBOL + ticket.getExhibitId() + SLASH_SYMBOL + ticket.getUserId());
            }
            if (ticket.getId() == null) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ticket.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException(DB_TICKET_SAVING_ERROR + ticket.getExhibitDate()
                                + SLASH_SYMBOL + ticket.getExhibitId() + SLASH_SYMBOL + ticket.getUserId());
                    }
                }
            }
            log.info(DB_TICKET_SAVED_ID + ticket.getId());
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new Exception(e);
        }
    }

    @Override
    public List<TicketDTO> findAllNotPaidUserTickets(Long userId) {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_TICKETS_BY_USER_ID_AND_NOT_PAID);
        List<TicketDTO> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            TicketDTOMapper ticketDTOMapper = new TicketDTOMapper();
            while (resultSet.next()) {
                result.add(ticketDTOMapper.extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Optional<Ticket> findTicketByExhibitIdAndExhibitDateAndUserIdAndNotPaid(
            Long exhibitId, LocalDate exhibitDate, Long userId) {

        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(
                SQL_QUERY_FIND_TICKET_BY_EXHIBIT_ID_AND_EXHIBIT_DATE_AND_USER_ID_AND_NOT_PAID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, exhibitId);
            preparedStatement.setObject(2, exhibitDate);
            preparedStatement.setLong(3, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new TicketMapper().extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public void deleteAllNotPaid(Long userId) throws TicketDeleteException {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_DELETE_ALL_NOT_PAID_TICKETS_FROM_CART);
        String message = DB_ALL_TICKETS_DELETING_FROM_CART_ERROR + userId;
        ticketDelete(userId, sqlQuery, message);
        log.info(DB_TICKETS_DELETED_FROM_CART + userId);
    }

    @Override
    public void deleteByIdNotPaid(Long ticketId) throws TicketDeleteException {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_DELETE_BY_ID_NOT_PAID_TICKET_FROM_CART);
        String message = DB_TICKET_DELETING_BY_ID_FROM_CART_ERROR + ticketId;
        ticketDelete(ticketId, sqlQuery, message);
        log.info(DB_TICKET_DELETED_FROM_CART_BY_ID + ticketId);
    }

    private void ticketDelete(Long ticketId, String sqlQuery, String message) throws TicketDeleteException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, ticketId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new TicketDeleteException(message);
            }
        } catch (TicketDeleteException e) {
            throw new TicketDeleteException(e.getMessage());
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReportDTO<UserStatisticDTO> getUserStatistic(Long userId, int pageNumber) {
        ReportDTO<UserStatisticDTO> result = new ReportDTO<>();
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_USER_STATISTIC_RECORD_QUANTITY);
        int recordQuantity = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                recordQuantity = resultSet.getInt(FIELD_DB_RECORD_QUANTITY);
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        if (recordQuantity > 0) {
            String sqlQuery2 = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_USER_STATISTIC);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery2)) {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, RECORD_PER_PAGE);
                preparedStatement.setLong(3, pageNumber * RECORD_PER_PAGE);
                ResultSet resultSet = preparedStatement.executeQuery();
                ObjectMapper<UserStatisticDTO> mapper = new UserStatisticDTOMapper();
                while (resultSet.next()) {
                    result.getItems().add(mapper.extractFromResultSet(resultSet));
                }
                result.setPageQuantity((recordQuantity - ONE_ELEMENT) / RECORD_PER_PAGE);
            } catch (Exception e) {
                log.error(MessageUtil.getRuntimeExceptionMessage(e));
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    @Override
    public ReportDTO<AdminStatisticDTO> getAdminStatistic(int pageNumber) {
        ReportDTO<AdminStatisticDTO> result = new ReportDTO<>();
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(
                SQL_QUERY_FIND_ADMIN_STATISTIC_RECORD_QUANTITY);
        int recordQuantity = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                recordQuantity = resultSet.getInt(FIELD_DB_RECORD_QUANTITY);
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        if (recordQuantity > 0) {
            String sqlQuery2 = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_ADMIN_STATISTIC);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery2)) {
                preparedStatement.setLong(1, RECORD_PER_PAGE);
                preparedStatement.setLong(2, pageNumber * RECORD_PER_PAGE);
                ResultSet resultSet = preparedStatement.executeQuery();
                ObjectMapper<AdminStatisticDTO> mapper = new AdminStatisticDTOMapper();
                while (resultSet.next()) {
                    result.getItems().add(mapper.extractFromResultSet(resultSet));
                }
                result.setPageQuantity(recordQuantity / RECORD_PER_PAGE);
            } catch (Exception e) {
                log.error(MessageUtil.getRuntimeExceptionMessage(e));
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    @Override
    public Optional<Ticket> findSumAndQuantityNotPaidUserTickets(Long userId) {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(
                SQL_QUERY_FIND_TICKETS_SUM_AND_QUANTITY_BY_USER_ID_AND_NOT_PAID);
        Optional<Ticket> result = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = Optional.of(new TicketPaymentMapper().extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(DB_CONNECTION_CLOSING_ERROR);
            throw new RuntimeException(e);
        }
    }
}
