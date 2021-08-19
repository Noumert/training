package project.service;

import javafx.scene.control.Pagination;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.exceptions.BanException;
import project.exceptions.NotEnoughMoneyException;
import project.entity.Account;
import project.entity.Payment;
import project.entity.StatusType;
import project.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    AccountService accountService;

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }


    public void saveNewPayment(Payment payment){
        payment.setPaymentNumber(randomPaymentNumber());
        try {
            paymentRepository.save(payment);
        }catch (RuntimeException e){
            throw new RuntimeException("something went wrong");
        }
    }

    public void save(Payment payment){
        try {
            paymentRepository.save(payment);
        }catch (RuntimeException e){
            throw new RuntimeException("something went wrong");
        }
    }

    @Transactional
    public Page<Payment> findUserPaymentsByUserId(Long userId, Pageable pageable){
        return paymentRepository.findByAccountIdIn(accountService
                .findUserAccountsByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()), pageable);
    }

    @Transactional
    public List<Payment> findUserPaymentsByUserId(Long userId){
        return paymentRepository.findByAccountIdIn(accountService
                .findUserAccountsByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }

    private String randomPaymentNumber() {
        return UUID.randomUUID().toString();
    }


    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }


    public  void setStatusById(StatusType status, Payment payment){
        payment.setStatus(status);
        this.save(payment);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor = {NotEnoughMoneyException.class})
    public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException {
        setStatusById(StatusType.SENT,payment);
        accountService.decreaseMoneyById(payment.getMoney(),payment.getAccount().getId());
    }
}
