package project.model;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.entity.CreditCard;
import project.dto.CreditCardDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */
@Component
public class CreditCardDtoConverterImpl implements EntityDtoConverter<CreditCard, CreditCardDTO> {
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM);

    @Override
    public CreditCardDTO convertEntityToDto(CreditCard creditCard) {
        return CreditCardDTO.builder()
                .id(creditCard.getId())
                .expirationDate(creditCard.getExpirationDate()
                        .format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .cardNumber(creditCard.getCardNumber())
                .build();
    }

    @Override
    public CreditCard convertDtoToEntity(CreditCardDTO creditCardDTO) {
        return CreditCard.builder()
                .id(creditCardDTO.getId())
                .expirationDate(LocalDate.parse(creditCardDTO.getExpirationDate(),
                        formatter.withLocale(LocaleContextHolder.getLocale())))
                .cardNumber(creditCardDTO.getCardNumber())
                .build();
    }

    @Override
    public List<CreditCardDTO> convertEntityListToDtoList(List<CreditCard> creditCards) {
        return creditCards.stream()
                .map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<CreditCard> convertDtoListToEntityList(List<CreditCardDTO> creditCardDTOS) {
        return creditCardDTOS.stream()
                .map(this::convertDtoToEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CreditCardDTO> convertEntityPageToDtoPage(Page<CreditCard> creditCards) {
        return creditCards.map(this::convertEntityToDto);
    }

    @Override
    public Page<CreditCard> convertDtoPageToEntityPage(Page<CreditCardDTO> creditCardDTOS) {
        return creditCardDTOS.map(this::convertDtoToEntity);
    }
}
