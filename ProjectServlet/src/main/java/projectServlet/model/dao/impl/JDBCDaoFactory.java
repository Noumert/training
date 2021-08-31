package projectServlet.model.dao.impl;

import projectServlet.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public AccountDao createAccountDao() {
        return new JDBCAccountDao(getConnection());
    }

    @Override
    public UnbanAccountRequestDao createUnbanAccountRequestDao() {
        return new JDBCUnbanAccountRequestDao(getConnection());
    }

    @Override
    public PaymentDao createPaymentDao() {
        return new JDBCPaymentDao(getConnection());
    }

    @Override
    public CreditCardDao createCreditCardDao() {
        return new JDBCCreditCardDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
