package project.service;

import javassist.NotFoundException;
import project.entity.Payment;
import project.exceptions.NotEnoughMoneyException;

/**
 * Created by Noumert on 21.08.2021.
 */
public interface PaymentProcessingService {
    void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException;
}
