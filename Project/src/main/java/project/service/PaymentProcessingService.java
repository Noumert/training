package project.service;

import javassist.NotFoundException;
import project.entity.Payment;
import project.exceptions.NotEnoughMoneyException;

public interface PaymentProcessingService {
    public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException;
}
