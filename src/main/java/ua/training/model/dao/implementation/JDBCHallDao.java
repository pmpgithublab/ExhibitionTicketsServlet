package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;
import ua.training.model.dao.HallDao;
import ua.training.model.dao.mapper.HallAdminMapper;
import ua.training.model.dao.mapper.HallMapper;
import ua.training.model.entity.Hall;
import ua.training.model.exception.NoDuplicationAllowedException;
import ua.training.model.util.DBQueryBundleManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.training.Constants.DB_CONNECTION_CLOSING_ERROR;

public class JDBCHallDao implements HallDao {
    private static final Logger log = Logger.getLogger(JDBCHallDao.class);
    private static final String SQL_QUERY_INSERT_NEW_HALL = "insert.new.hall";
    private static final String SQL_QUERY_UPDATE_HALL = "update.hall";
    private static final String SQL_QUERY_SELECT_HALLS = "select.halls";
    private static final String SQL_QUERY_FIND_HALL_BY_ID = "find.hall.by.id";

    private final Connection connection;


    public JDBCHallDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Hall hall) throws NoDuplicationAllowedException {
        String sqlQuery;
        if (hall.getId() == null) {
            sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_INSERT_NEW_HALL);
        } else {
            sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_UPDATE_HALL);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, hall.getName());
            preparedStatement.setString(2, hall.getNameUK());
            if (hall.getId() != null) {
                preparedStatement.setLong(3, hall.getId());
            }
            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NoDuplicationAllowedException(e);
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Hall> findById(Long id) {
        String sqlQuery = DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_HALL_BY_ID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new HallAdminMapper().extractFromResultSet(resultSet));
            }
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }


    @Override
    public List<Hall> findAll() {
        List<Hall> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet =
                    statement.executeQuery(DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_SELECT_HALLS));

            HallMapper hallMapper = new HallMapper();
            while (resultSet.next()) {
                result.add(hallMapper.extractFromResultSet(resultSet));
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
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }
}
