package project.model;

import org.junit.jupiter.api.Test;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.PaymentDtoConverterImpl;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.PaymentDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class PaymentDtoConverterImplTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    EntityDtoConverter<Payment, PaymentDTO> paymentDtoConverter = new PaymentDtoConverterImpl();
    EntityDtoConverter<Account, AccountDTO> accountDtoConverter = new AccountDtoConverterImpl();



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
        assertThat(paymentDTO.getDateTime()).isEqualTo(localDateTime.format(formatter.withLocale(Locale.getDefault())));
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
                .dateTime(localDateTime.format(formatter.withLocale(Locale.getDefault())))
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