package projectServlet.model.service;


import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import javax.ws.rs.NotFoundException;

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
        paymentService.setStatusByPayment(StatusType.SENT, payment);
        accountService.decreaseMoneyById(payment.getMoney(), payment.getAccount().getId());
    }
}
