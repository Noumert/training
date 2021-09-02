package project.model;


import org.junit.jupiter.api.Test;
import projectServlet.model.converters.CreditCardDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.dto.CreditCardDTO;
import projectServlet.model.entity.CreditCard;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreditCardDtoConverterImplTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM);

    EntityDtoConverter<CreditCard, CreditCardDTO> creditCardDtoConverter = new CreditCardDtoConverterImpl();

    @Test
    void convertEntityToDto() {
        LocalDate localDate = LocalDate.now();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .expirationDate(localDate)
                .cardNumber("1234-sdf-1234")
                .build();
        CreditCardDTO creditCardDTO = creditCardDtoConverter.convertEntityToDto(creditCard);
        assertThat(creditCardDTO.getId()).isEqualTo(1L);
        assertThat(creditCardDTO.getExpirationDate()).isEqualTo(localDate.format(formatter.withLocale(Locale.getDefault())));
        assertThat(creditCardDTO.getCardNumber()).isEqualTo("1234-sdf-1234");
    }

    @Test
    void convertDtoToEntity() {
        LocalDate localDate = LocalDate.now();
        CreditCardDTO creditCardDTO = CreditCardDTO.builder()
                .id(1L)
                .expirationDate(localDate
                        .format(formatter.withLocale(Locale.getDefault())))
                .cardNumber("1234-sdf-1234")
                .build();
        CreditCard creditCard = creditCardDtoConverter.convertDtoToEntity(creditCardDTO);
        assertThat(creditCard.getId()).isEqualTo(1L);
        assertThat(creditCard.getExpirationDate()).isEqualTo(localDate);
        assertThat(creditCard.getCardNumber()).isEqualTo("1234-sdf-1234");
    }
}