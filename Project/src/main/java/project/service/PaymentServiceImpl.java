package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.entity.Payment;
import project.entity.StatusType;
import project.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;


/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class PaymentServiceImpl implements  PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Page<Payment> findByUserId(Long userId, Pageable pageable) {
        return paymentRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<Payment> findByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public Payment setStatusByPayment(StatusType status, Payment payment) {
        payment.setStatus(status);
        return this.save(payment);
    }
}
