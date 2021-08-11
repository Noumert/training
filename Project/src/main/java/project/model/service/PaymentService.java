package project.model.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.exceptions.NotEnoughMoneyException;
import project.model.entity.Account;
import project.model.entity.Payment;
import project.model.entity.StatusType;
import project.model.repository.PaymentRepository;

import javax.transaction.Transactional;
import java.util.List;
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
        payment.setPaymentNumber(generatePaymentNumber());
        paymentRepository.save(payment);
    }

    public List<Payment> findCurrentUserPayments() throws NotFoundException {
        return paymentRepository.findByAccountIdIn(accountService
                .findCurrentUserAccounts()
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
    }

    private String generatePaymentNumber() {
        List<String> paymentNumbers = paymentRepository.findAll().stream().map(Payment::getPaymentNumber).collect(Collectors.toList());
        String paymentNumber = randomPaymentNumber();
        while (paymentNumbers.contains(paymentNumber)) {
            paymentNumber = randomPaymentNumber();
        }
        return paymentNumber;
    }

    private String randomPaymentNumber() {
        return  random()
                + random()
                + random()
                + random()
                + random();
    }

    private String random() {
        return String.valueOf((int) Math.floor(Math.random() * (AccountService.MAX_RANDOM - AccountService.MIN_RANDOM + 1) + AccountService.MIN_RANDOM));
    }

    public Payment findById(Long paymentId) throws NotFoundException {
        return paymentRepository.findById(paymentId).orElseThrow(()->new NotFoundException("No such payment"));
    }


    public  void setStatusById(StatusType status, Long paymentId){
        paymentRepository.setStatusById(status,paymentId);
    }

    @Transactional
    public void sendPayment(Payment payment) throws NotFoundException, NotEnoughMoneyException {
        setStatusById(StatusType.SENT,payment.getId());
        accountService.decreaseMoneyById(payment.getMoney(),payment.getAccount().getId());
    }
}
