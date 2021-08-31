package projectServlet.model.service;

import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface PaymentService {

        List<Payment> findAll();

        void save(Payment payment);

//        Page<Payment> findPaymentsByUserId(Long userId, Pageable pageable);

        List<Payment> findPaymentsByUserId(Long userId);

        Optional<Payment> findById(Long paymentId);

        void setStatusByPayment(StatusType status, Payment payment);
}
