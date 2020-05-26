package ua.training.model.dao.implementation;

import org.apache.log4j.Logger;
import ua.training.model.dao.*;
import ua.training.util.MessageUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private static final Logger log = Logger.getLogger(JDBCDaoFactory.class);
    private static final String DB_CONNECTION_CREATION_ERROR = "DB connection creation error";

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public HallDao createHallDao() {
        return new JDBCHallDao(getConnection());
    }

    @Override
    public ExhibitDao createExhibitDao() {
        return new JDBCExhibitDao(getConnection());
    }

    @Override
    public TicketDao createTicketDao() {
        return new JDBCTicketDao(getConnection());
    }

    @Override
    public PaymentDao createPaymentDao() {
        return new JDBCPaymentDao(getConnection());
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error(DB_CONNECTION_CREATION_ERROR);
            log.error(MessageUtil.getRuntimeExceptionMessage(e));
            throw new RuntimeException(e);
        }
    }
}
