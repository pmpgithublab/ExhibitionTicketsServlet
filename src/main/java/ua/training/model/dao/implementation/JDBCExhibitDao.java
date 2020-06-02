package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.util.LocaleUtil;
import ua.training.util.MessageUtil;
import ua.training.model.dao.ExhibitDao;
import ua.training.model.dao.mapper.*;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.entity.Exhibit;
import ua.training.model.exception.NoDuplicationAllowedException;
import ua.training.model.util.DBQueryBundleManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.training.Constants.*;

public class JDBCExhibitDao implements ExhibitDao {
    private static final Logger log = Logger.getLogger(JDBCExhibitDao.class);
    private static final String SQL_QUERY_INSERT_NEW_EXHIBIT = "insert.new.exhibit";
    private static final String SQL_QUERY_UPDATE_EXHIBIT = "update.exhibit";
    private static final String SQL_QUERY_SELECT_EXHIBITS = "select.exhibits";
    private static final String SQL_QUERY_FIND_EXHIBIT_BY_ID = "find.exhibit.by.id";
    private static final String SQL_QUERY_SELECT_CURRENT_EXHIBIT_NAMES = "select.current.exhibit.names";
    private static final String SQL_QUERY_SELECT_CURRENT_EXHIBITS_WITH_HALL = "find.actual.exhibits.by.id.with.hall";
    private static final String DB_EXHIBIT_SAVING_ERROR = "Exhibit saving error";

    private final Connection connection;


    public JDBCExhibitDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Exhibit exhibit) throws Exception {
        String sqlQuery;
        if (exhibit.getId() == null) {
            sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_INSERT_NEW_EXHIBIT);
        } else {
            sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_UPDATE_EXHIBIT);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, exhibit.getName());
            preparedStatement.setString(2, exhibit.getNameUK());
            preparedStatement.setObject(3, exhibit.getStartDateTime());
            preparedStatement.setObject(4, exhibit.getEndDateTime());
            preparedStatement.setInt(5, exhibit.getMaxVisitorsPerDay());
            preparedStatement.setLong(6, exhibit.getTicketCost());
            preparedStatement.setLong(7, exhibit.getHallId());
            if (exhibit.getId() != null) {
                preparedStatement.setLong(8, exhibit.getId());
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(DB_EXHIBIT_SAVING_ERROR + exhibit.getName()
                        + SLASH_SYMBOL + exhibit.getNameUK());
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NoDuplicationAllowedException(e);
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Exhibit> findById(Long id) {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_EXHIBIT_BY_ID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new ExhibitAdminMapper().extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Exhibit> findAll() {
        List<Exhibit> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    LocaleUtil.localizeQuery(DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_SELECT_EXHIBITS)));

            ObjectMapper<Exhibit> mapper = new ExhibitAdminListMapper();
            while (resultSet.next()) {
                result.add(mapper.extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Exhibit> findCurrentExhibits() {
        List<Exhibit> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LocaleUtil.localizeQuery(
                DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_SELECT_CURRENT_EXHIBIT_NAMES)))) {

            preparedStatement.setObject(1, LocalDateTime.now());
            ResultSet resultSet = preparedStatement.executeQuery();

            ObjectMapper<Exhibit> mapper = new ExhibitNameMapper();
            while (resultSet.next()) {
                result.add(mapper.extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Optional<ExhibitDTO> findByIdWithHall(Long exhibitId) {
        String sqlQuery = LocaleUtil.localizeQuery(DBQueryBundleManager.INSTANCE.getProperty(
                                                            SQL_QUERY_SELECT_CURRENT_EXHIBITS_WITH_HALL));
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setObject(1, LocalDateTime.now());
            preparedStatement.setLong(2, exhibitId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new ExhibitTradeMapper().extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return Optional.empty();
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
