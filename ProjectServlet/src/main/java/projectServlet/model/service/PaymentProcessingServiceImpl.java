package projectServlet.model.service;


import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.dao.impl.ConnectionPoolHolder;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import javax.ws.rs.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Noumert on 21.08.2021.
 */

public class PaymentProcessingServiceImpl implements PaymentProcessingService{
    private PaymentService paymentService = new PaymentServiceImpl();
    private AccountService accountService = new AccountServiceImpl();

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {NotEnoughMoneyException.class})
    //TODO transactional
    public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException {
//        try(Connection connection = ConnectionPoolHolder.getDataSource().getConnection()){
//            connection.setAutoCommit(false);
//            try{
//                paymentService.setStatusByPayment(StatusType.SENT, payment);
//                accountService.decreaseMoneyById(payment.getMoney(), payment.getAccount().getId());
//                connection.commit();
//            }catch (Throwable e){
//                connection.rollback();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException();
//        }
        paymentService.setStatusByPayment(StatusType.SENT, payment);
        accountService.decreaseMoneyById(payment.getMoney(), payment.getAccount().getId());
    }
}
