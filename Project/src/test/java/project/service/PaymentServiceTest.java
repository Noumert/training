package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.entity.Payment;
import project.entity.StatusType;
import project.repository.PaymentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void saveSuccess() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());

        assertThat(paymentService.save(payment)).isEqualTo(payment);
    }

    @Test
    public void saveException() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(paymentRepository.save(any(Payment.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> paymentService.save(payment));
    }

    @Test
    void findAll() {
        Payment payment1 = Payment.builder()
                .id(1L)
                .build();
        Payment payment2 = Payment.builder()
                .id(2L)
                .build();
        List<Payment> payments = Arrays.asList(payment1, payment2);
        Mockito.when(paymentRepository.findAll()).thenReturn(payments);

        assertThat(paymentService.findAll()).isEqualTo(payments);
    }

    @Test
    void findPaymentsByUserId() {
        Payment payment1 = Payment.builder()
                .id(1L)
                .build();
        Payment payment2 = Payment.builder()
                .id(2L)
                .build();
        List<Payment> payments = Arrays.asList(payment1, payment2);
        Mockito.when(paymentRepository.findPaymentsByUserId(1L)).thenReturn(payments);

        assertThat(paymentService.findPaymentsByUserId(1L)).isEqualTo(payments);

    }

    @Test
    void findPaymentsByUserIdPage() {
        Payment payment1 = Payment.builder()
                .id(1L)
                .build();
        Payment payment2 = Payment.builder()
                .id(2L)
                .build();
        Page<Payment> payments = new PageImpl<>(Arrays.asList(payment1, payment2));
        Pageable pageable = PageRequest.of(1, 2);
        Mockito.when(paymentRepository.findPaymentsByUserId(1L, pageable)).thenReturn(payments);

        assertThat(paymentService.findPaymentsByUserId(1L, pageable)).isEqualTo(payments);
    }

    @Test
    void findById() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        assertThat(paymentService.findById(1L)).isEqualTo(Optional.of(payment));
    }

    @Test
    void setStatusByPayment() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());

        assertThat(paymentService.setStatusByPayment(StatusType.SENT,payment).getStatus()).isEqualTo(StatusType.SENT);
    }
}