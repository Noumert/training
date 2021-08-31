package project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.entity.Payment;
import project.entity.StatusType;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface PaymentService {

        List<Payment> findAll();

        Payment save(Payment payment);

        Page<Payment> findByUserId(Long userId, Pageable pageable);

        List<Payment> findByUserId(Long userId);

        Optional<Payment> findById(Long paymentId);

        Payment setStatusByPayment(StatusType status, Payment payment);
}
