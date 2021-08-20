package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.dto.*;
import project.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentConverterImpl implements EntityDtoConverter<Payment, PaymentDTO> {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);

    @Autowired
    EntityDtoConverter<Account, AccountDTO> accountDtoConverter;
    @Autowired
    MoneyFormatConverter moneyFormatConverter;


    @Override
    public PaymentDTO convertEntityToDto(Payment payment) {
        return PaymentDTO
                .builder()
                .account(accountDtoConverter.convertEntityToDto(payment.getAccount()))
                .id(payment.getId())
                .dateTime(payment.getDateTime().format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .paymentNumber(payment.getPaymentNumber())
                .recipient(payment.getRecipient())
                .status(payment.getStatus().name())
                .money(moneyFormatConverter.getStringMoneyFromMoneyValue(payment.getMoney()))
                .build();
    }

    @Override
    public Payment convertDtoToEntity(PaymentDTO paymentDTO) {
                return Payment
                .builder()
                .account(accountDtoConverter.convertDtoToEntity(paymentDTO.getAccount()))
                .id(paymentDTO.getId())
                .dateTime(LocalDateTime.parse(paymentDTO.getDateTime(), formatter.withLocale(LocaleContextHolder.getLocale())))
                .paymentNumber(paymentDTO.getPaymentNumber())
                .recipient(paymentDTO.getRecipient())
                .status(StatusType.valueOf(paymentDTO.getStatus()))
                .money(moneyFormatConverter.getMoneyValue(paymentDTO.getMoney()))
                .build();
    }

    @Override
    public List<PaymentDTO> convertEntityListToDtoList(List<Payment> payments) {
        return payments.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> convertDtoListToPaymentList(List<PaymentDTO> paymentDTOS) {
        return paymentDTOS.stream()
                .map(this::convertDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PaymentDTO> convertEntityPageToDtoPage(Page<Payment> payments) {
        return payments.map(this::convertEntityToDto);
    }

    @Override
    public Page<Payment> convertDtoPageToEntityPage(Page<PaymentDTO> payments) {
        return payments.map(this::convertDtoToEntity);
    }


}
