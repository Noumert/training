package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Account;
import project.entity.Payment;
import project.entity.StatusType;
import project.exceptions.NotEnoughMoneyException;
import project.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface PaymentService {

        public List<Payment> findAll();

        public void save(Payment payment);

        public Page<Payment> findUserPaymentsByUserId(Long userId, Pageable pageable);

        public List<Payment> findUserPaymentsByUserId(Long userId);

        public Optional<Payment> findById(Long paymentId);

        public void setStatusByPayment(StatusType status, Payment payment);

        public void sendPayment(Payment payment) throws NotEnoughMoneyException, NotFoundException;

}
