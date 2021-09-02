package projectServlet.model.service;


import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.dao.AccountDao;
import projectServlet.model.dao.PaymentDao;
import projectServlet.model.dao.impl.ConnectionPoolHolder;
import projectServlet.model.dao.impl.JDBCAccountDao;
import projectServlet.model.dao.impl.JDBCPaymentDao;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import javax.ws.rs.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Noumert on 21.08.2021.
 */

public class PaymentProcessingServiceImpl implements PaymentProcessingService{


    @Override
    public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException {
        try(Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
            PaymentDao paymentDao = new JDBCPaymentDao(connection);
            AccountDao accountDao = new JDBCAccountDao(connection)){
            connection.setAutoCommit(false);
            try{
                payment.setStatus(StatusType.SENT);
                paymentDao.save(payment);
                Account account = accountDao.findById(payment.getAccount().getId()).orElseThrow(NotFoundException::new);
                account.setMoney(account.getMoney() - payment.getMoney());
                if (account.getMoney() < 0) {
                    throw new NotEnoughMoneyException("money can't be negative");
                }
                accountDao.save(account);
                connection.commit();
            }catch (SQLException | NotFoundException | NotEnoughMoneyException exception){
                connection.rollback();
                throw exception;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
