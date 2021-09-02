package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.PaymentDao;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;
import projectServlet.model.entity.User;
import projectServlet.model.service.PaymentServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentDao paymentDao;
    @Mock
    private DaoFactory daoFactory;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void saveSuccess() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doNothing().when(paymentDao).save(any(Payment.class));

        assertDoesNotThrow(()->paymentService.save(payment));
    }

    @Test
    public void saveException() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doThrow(RuntimeException.class).when(paymentDao).save(any(Payment.class));

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
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doReturn(payments).when(paymentDao).findAll();

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
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doReturn(payments).when(paymentDao).findByUserId(1L);

        assertThat(paymentService.findByUserId(1L)).isEqualTo(payments);

    }

    @Test
    void findPaymentsByUserIdPage() {
        Payment payment1 = Payment.builder()
                .id(1L)
                .build();
        Payment payment2 = Payment.builder()
                .id(2L)
                .build();
        List<Payment> payments = Arrays.asList(payment1, payment2);
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doReturn(payments).when(paymentDao).findByUserId(1L,1,5,"payment_id",true);

        assertThat(paymentService.findByUserId(1L,1,5,"payment_id",true)).isEqualTo(payments);
    }

    @Test
    void findById() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doReturn(Optional.of(payment)).when(paymentDao).findById(1L);

        assertThat(paymentService.findById(1L)).isEqualTo(Optional.of(payment));
    }

    @Test
    void setStatusByPayment() {
        Payment payment = Payment.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doNothing().when(paymentDao).save(payment);

        assertDoesNotThrow(()->paymentService.setStatusByPayment(StatusType.SENT,payment));
    }
}