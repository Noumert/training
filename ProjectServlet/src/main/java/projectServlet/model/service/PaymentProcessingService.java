package projectServlet.model.service;

import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.entity.Payment;

import javax.ws.rs.NotFoundException;

/**
 * Created by Noumert on 21.08.2021.
 */
public interface PaymentProcessingService {
    void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException;
}
