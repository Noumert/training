package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.exceptions.NotEnoughMoneyException;
import project.entity.Account;
import project.entity.Payment;
import project.entity.StatusType;
import project.repository.PaymentRepository;


import java.util.List;
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

    @Transactional
    public List<Payment> findUserPaymentsByUserId(Long userId){
        return paymentRepository.findByAccountIdIn(accountService
                .findUserAccountsByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }

    @Transactional
    public List<Payment> findUserPaymentsByUserIdOrderByPaymentNumber(Long userId){
        return paymentRepository.findByAccountIdInOrderByPaymentNumber(accountService
                .findUserAccountsByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }

    @Transactional
    public List<Payment> findUserPaymentsByUserIdOrderByDateTimeDesc(Long userId){
        return paymentRepository.findByAccountIdInOrderByDateTimeDesc(accountService
                .findUserAccountsByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }

    @Transactional
    public List<Payment> findUserPaymentsByUserIdOrderByDateTimeAsc(Long userId){
        return paymentRepository.findByAccountIdInOrderByDateTimeAsc(accountService
                .findUserAccountsByUserId(userId)
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }


    private String randomPaymentNumber() {
        return UUID.randomUUID().toString();
    }


    public Payment findById(Long paymentId) throws NotFoundException {
        return paymentRepository.findById(paymentId).orElseThrow(()->new NotFoundException("No such payment"));
    }


    public  void setStatusById(StatusType status, Long paymentId){
        paymentRepository.setStatusById(status,paymentId);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor = NotEnoughMoneyException.class)
    public void sendPayment(Payment payment) throws NotFoundException, NotEnoughMoneyException {
        setStatusById(StatusType.SENT,payment.getId());
        accountService.decreaseMoneyById(payment.getMoney(),payment.getAccount());
    }
}
