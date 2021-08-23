package project.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import project.dto.AccountDTO;
import project.dto.UnbanAccountRequestDTO;
import project.entity.Account;
import project.entity.UnbanAccountRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UnbanAccountRequestDtoConverterImplTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    @Autowired
    EntityDtoConverter<UnbanAccountRequest, UnbanAccountRequestDTO> unbanAccountRequestDtoConverter;
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
        UnbanAccountRequest unbanAccountRequest = UnbanAccountRequest
                .builder()
                .id(1L)
                .dateTime(localDateTime)
                .resolved(true)
                .account(account)
                .build();
        UnbanAccountRequestDTO unbanAccountRequestDTO =
                unbanAccountRequestDtoConverter.convertEntityToDto(unbanAccountRequest);
        assertThat(unbanAccountRequestDTO.getId()).isEqualTo(1L);
        assertThat(unbanAccountRequestDTO.getDateTime()).isEqualTo(localDateTime.format(formatter.withLocale(LocaleContextHolder.getLocale())));
        assertThat(unbanAccountRequestDTO.isResolved()).isEqualTo(true);
        assertThat(unbanAccountRequestDTO.getAccount()).isEqualTo(accountDtoConverter.convertEntityToDto(account));
    }

    @Test
    void convertDtoToEntity() {
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        AccountDTO accountDTO = AccountDTO
                .builder()
                .id(1L)
                .accountName("U23-234as-1234")
                .accountNumber("123-234as-1234")
                .money("30.00")
                .ban(true)
                .build();
        UnbanAccountRequestDTO unbanAccountRequestDTO = UnbanAccountRequestDTO
                .builder()
                .id(1L)
                .dateTime(localDateTime.format(formatter.withLocale(LocaleContextHolder.getLocale())))
                .resolved(true)
                .account(accountDTO)
                .build();
        UnbanAccountRequest unbanAccountRequest =
                unbanAccountRequestDtoConverter.convertDtoToEntity(unbanAccountRequestDTO);
        assertThat(unbanAccountRequest.getId()).isEqualTo(1L);
        assertThat(unbanAccountRequest.getDateTime()).isEqualTo(localDateTime);
        assertThat(unbanAccountRequest.isResolved()).isEqualTo(true);
        assertThat(unbanAccountRequest.getAccount()).isEqualTo(accountDtoConverter.convertDtoToEntity(accountDTO));
    }
}