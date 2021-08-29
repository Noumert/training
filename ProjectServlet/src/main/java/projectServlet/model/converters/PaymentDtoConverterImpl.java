package projectServlet.model.converters;


import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.PaymentDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */
public class PaymentDtoConverterImpl implements EntityDtoConverter<Payment, PaymentDTO> {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);

    private EntityDtoConverter<Account, AccountDTO> accountDtoConverter = new AccountDtoConverterImpl();

    private MoneyFormatConverter moneyFormatConverter = new MoneyFormatConverterImpl();


    @Override
    public PaymentDTO convertEntityToDto(Payment payment) {
        return PaymentDTO
                .builder()
                .account(accountDtoConverter.convertEntityToDto(payment.getAccount()))
                .id(payment.getId())
                .dateTime(payment.getDateTime().format(formatter.withLocale(Locale.getDefault())))
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
                .dateTime(LocalDateTime.parse(paymentDTO.getDateTime(), formatter.withLocale(Locale.getDefault())))
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
    public List<Payment> convertDtoListToEntityList(List<PaymentDTO> paymentDTOS) {
        return paymentDTOS.stream()
                .map(this::convertDtoToEntity)
                .collect(Collectors.toList());
    }

//    @Override
//    public Page<PaymentDTO> convertEntityPageToDtoPage(Page<Payment> payments) {
//        return payments.map(this::convertEntityToDto);
//    }
//
//    @Override
//    public Page<Payment> convertDtoPageToEntityPage(Page<PaymentDTO> payments) {
//        return payments.map(this::convertDtoToEntity);
//    }


}
