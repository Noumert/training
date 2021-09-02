package project.model;

import org.junit.jupiter.api.Test;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.UnbanAccountRequestDtoConverterImpl;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.dto.UnbanAccountRequestDTO;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.UnbanAccountRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UnbanAccountRequestDtoConverterImplTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    EntityDtoConverter<UnbanAccountRequest, UnbanAccountRequestDTO> unbanAccountRequestDtoConverter = new UnbanAccountRequestDtoConverterImpl();
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
        assertThat(unbanAccountRequestDTO.getDateTime()).isEqualTo(localDateTime.format(formatter.withLocale(Locale.getDefault())));
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
                .dateTime(localDateTime.format(formatter.withLocale(Locale.getDefault())))
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