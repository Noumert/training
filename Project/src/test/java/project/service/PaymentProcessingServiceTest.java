package project.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.Account;
import project.entity.Payment;
import project.entity.StatusType;
import project.exceptions.NotEnoughMoneyException;
import project.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentProcessingServiceTest {
    @Mock
    private AccountService accountService;
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentProcessingServiceImpl paymentProcessingService;

    @Test
    void sendPaymentSuccess() throws NotEnoughMoneyException, NotFoundException {
        Account account = Account.builder().id(1L).money(400L).build();
        Payment payment = Payment.builder().id(1L).money(300L).account(account).build();
        Mockito.when(accountService.decreaseMoneyById(payment.getMoney(),payment.getAccount().getId()))
                .thenReturn(account);
        Mockito.when(paymentService.setStatusByPayment(StatusType.SENT,payment)).thenReturn(payment);

        assertDoesNotThrow(()->paymentProcessingService.sendPayment(payment));
        verify(accountService,only()).decreaseMoneyById(payment.getMoney(),payment.getAccount().getId());
        verify(paymentService,only()).setStatusByPayment(StatusType.SENT,payment);
    }

    @Test
    void sendPaymentNotEnoughMoneyException() throws NotEnoughMoneyException, NotFoundException {
        Account account = Account.builder().id(1L).money(100L).build();
        Payment payment = Payment.builder().id(1L).money(300L).account(account).build();
        Mockito.when(accountService.decreaseMoneyById(payment.getMoney(),payment.getAccount().getId()))
                .thenThrow(NotEnoughMoneyException.class);
        Mockito.when(paymentService.setStatusByPayment(StatusType.SENT,payment)).thenReturn(payment);

        assertThrows(NotEnoughMoneyException.class, () -> paymentProcessingService.sendPayment(payment));
    }

    @Test
    void sendPaymentNotFoundException() throws NotEnoughMoneyException, NotFoundException {
        Account account = Account.builder().id(1L).money(100L).build();
        Payment payment = Payment.builder().id(1L).money(300L).account(account).build();
        Mockito.when(accountService.decreaseMoneyById(payment.getMoney(),payment.getAccount().getId()))
                .thenThrow(NotFoundException.class);
        Mockito.when(paymentService.setStatusByPayment(StatusType.SENT,payment)).thenReturn(payment);

        assertThrows(NotFoundException.class, () -> paymentProcessingService.sendPayment(payment));
    }
}