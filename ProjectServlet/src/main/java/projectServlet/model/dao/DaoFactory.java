package projectServlet.model.dao;

import projectServlet.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract AccountDao createAccountDao();

    public abstract UnbanAccountRequestDao createUnbanAccountRequestDao();

    public abstract PaymentDao createPaymentDao();

    public abstract CreditCardDao createCreditCardDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
