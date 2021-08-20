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

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Transactional
    public Page<Payment> findUserPaymentsByUserId(Long userId, Pageable pageable) {
        return paymentRepository.findByAccountIdIn(accountService
                .findByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()), pageable);
    }

    @Transactional
    public List<Payment> findUserPaymentsByUserId(Long userId) {
        return paymentRepository.findByAccountIdIn(accountService
                .findByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }

    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }


    public void setStatusByPayment(StatusType status, Payment payment) {
        payment.setStatus(status);
        this.save(payment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {NotEnoughMoneyException.class})
    public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException {
        setStatusByPayment(StatusType.SENT, payment);
        accountService.decreaseMoneyById(payment.getMoney(), payment.getAccount().getId());
    }
}
