package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Payment;
import project.entity.StatusType;
import project.exceptions.NotEnoughMoneyException;

/**
 * Created by Noumert on 21.08.2021.
 */
@Service
public class PaymentProcessingServiceImpl implements PaymentProcessingService{
    @Autowired
    PaymentService paymentService;
    @Autowired
    AccountService accountService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {NotEnoughMoneyException.class})
    public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException {
        paymentService.setStatusByPayment(StatusType.SENT, payment);
        accountService.decreaseMoneyById(payment.getMoney(), payment.getAccount().getId());
    }
}
