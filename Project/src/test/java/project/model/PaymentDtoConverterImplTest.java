package project.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import project.dto.AccountDTO;
import project.dto.PaymentDTO;
import project.entity.Account;
import project.entity.Payment;
import project.entity.StatusType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PaymentDtoConverterImplTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    @Autowired
    EntityDtoConverter<Payment, PaymentDTO> paymentDtoConverter;
    @Autowired
    EntityDtoConverter<Account, AccountDTO> accountDtoConverter;



    @Test
    void convertEntityToDto() {
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        Account account = Account
                .builder()
                .id(1L)
                .accountName("U23-234as-1234")
                .accountNumber("123-234as-1234")
                .money(3000L)
                .ban(true)
                .build();
        Payment payment = Payment
                .builder()
                .account(account)
                .id(1L)
                .dateTime(localDateTime)
                .paymentNumber("123-234as-1234")
                .recipient("Test")
                .status(StatusType.PREPARED)
                .money(300L)
                .build();

        PaymentDTO paymentDTO = paymentDtoConverter.convertEntityToDto(payment);
        assertThat(paymentDTO.getId()).isEqualTo(1L);
        assertThat(paymentDTO.getStatus()).isEqualTo(StatusType.PREPARED.name());
        assertThat(paymentDTO.getRecipient()).isEqualTo("Test");
        assertThat(paymentDTO.getDateTime()).isEqualTo(localDateTime.format(formatter.withLocale(LocaleContextHolder.getLocale())));
        assertThat(paymentDTO.getPaymentNumber()).isEqualTo("123-234as-1234");
        assertThat(paymentDTO.getAccount()).isEqualTo(accountDtoConverter.convertEntityToDto(account));
        assertThat(paymentDTO.getMoney()).isEqualTo("3.00");
    }

    @Test
    void convertDtoToEntity() {
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        AccountDTO accountDTO = AccountDTO
                .builder()
                .id(1L)
                .accountName("U23-234as-1234")
                .accountNumber("123-234as-1234")
                .money("30.oo")
                .ban(true)
                .build();
        PaymentDTO paymentDTO = PaymentDTO
                .builder()
                .account(accountDTO)
                .id(1L)
                .dateTime(localDateTime.format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .paymentNumber("123-234as-1234")
                .recipient("Test")
                .status(StatusType.PREPARED.name())
                .money("3.00")
                .build();
        Payment payment = paymentDtoConverter.convertDtoToEntity(paymentDTO);
        assertThat(payment.getId()).isEqualTo(1L);
        assertThat(payment.getStatus()).isEqualTo(StatusType.PREPARED);
        assertThat(payment.getRecipient()).isEqualTo("Test");
        assertThat(payment.getDateTime()).isEqualTo(localDateTime);
        assertThat(payment.getPaymentNumber()).isEqualTo("123-234as-1234");
        assertThat(payment.getAccount()).isEqualTo(accountDtoConverter.convertDtoToEntity(accountDTO));
        assertThat(payment.getMoney()).isEqualTo(300L);
    }
}