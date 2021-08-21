package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.exceptions.NotEnoughMoneyException;
import project.entity.Account;
import project.entity.Payment;
import project.entity.StatusType;
import project.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PaymentServiceImpl implements  PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    AccountServiceImpl accountService;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Page<Payment> findPaymentsByUserId(Long userId, Pageable pageable) {
        return paymentRepository.findPaymentsByUserId(userId, pageable);
    }
    
    @Override
    public List<Payment> findPaymentsByUserId(Long userId) {
        return paymentRepository.findPaymentsByUserId(userId);
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public void setStatusByPayment(StatusType status, Payment payment) {
        payment.setStatus(status);
        this.save(payment);
    }
}
