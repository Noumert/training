package project.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.entity.Account;
import project.model.entity.Payment;
import project.model.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }


    public void saveNewPayment(Payment payment){
        payment.setPaymentNumber(generatePaymentNumber());
        paymentRepository.save(payment);
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
}
