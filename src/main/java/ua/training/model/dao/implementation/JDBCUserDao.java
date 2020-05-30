package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.util.MessageUtil;
import ua.training.model.util.DBQueryBundleManager;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.entity.User;
import ua.training.model.exception.SuchEmailExistsException;

import java.sql.*;
import java.util.Optional;

import static ua.training.Constants.*;

public class JDBCUserDao implements UserDao {
    private static final Logger log = Logger.getLogger(JDBCUserDao.class);

    private static final String SQL_QUERY_INSERT_NEW_USER = "insert.new.user";
    private static final String SQL_QUERY_FIND_USER_BY_EMAIL_AND_PASSWORD = "find.user.by.email.and.password";

    private static final String DB_USER_CREATION_ERROR_SUCH_EMAIL_EXISTS = "DB user creation error: such email exists.";
    private static final String DB_USER_CREATED = "DB user created: ";

    private final Connection connection;


    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(User user) throws SuchEmailExistsException {
        try (PreparedStatement ps = connection.prepareStatement(
                DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_INSERT_NEW_USER))) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setString(4, user.getName());
            ps.setString(5, user.getNameUK());
            ps.execute();
            log.info(DB_USER_CREATED + user.getEmail());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SuchEmailExistsException(DB_USER_CREATION_ERROR_SUCH_EMAIL_EXISTS, user.getEmail());
        } catch (Exception e) {
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();
        try (PreparedStatement ps = connection.prepareCall(
                DBQueryBundleManager.INSTANCE.getProperty(SQL_QUERY_FIND_USER_BY_EMAIL_AND_PASSWORD))) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(new UserMapper().extractFromResultSet(resultSet));
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
