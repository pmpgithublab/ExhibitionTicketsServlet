package ua.training.model.dao;

import ua.training.model.dao.implementation.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract HallDao createHallDao();
    public abstract ExhibitDao createExhibitDao();
    public abstract TicketDao createTicketDao();
    public abstract PaymentDao createPaymentDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }

        return daoFactory;
    }
}
