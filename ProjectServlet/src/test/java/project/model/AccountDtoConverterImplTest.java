package project.model;

import org.junit.jupiter.api.Test;
import projectServlet.model.converters.AccountDtoConverterImpl;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.dto.AccountDTO;
import projectServlet.model.entity.Account;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AccountDtoConverterImplTest {
    EntityDtoConverter<Account, AccountDTO> accountDtoConverter = new AccountDtoConverterImpl();

    @Test
    void convertEntityToDto() {
        Account account = Account.builder()
            .id(1L)
            .ban(true)
            .accountName("U1234-df23-214")
            .accountNumber("4321-df23-214")
            .money(3000L)
            .build();
        AccountDTO accountDTO = accountDtoConverter.convertEntityToDto(account);
        assertThat(accountDTO.getId()).isEqualTo(1L);
        assertThat(accountDTO.isBan()).isEqualTo(true);
        assertThat(accountDTO.getAccountName()).isEqualTo("U1234-df23-214");
        assertThat(accountDTO.getAccountNumber()).isEqualTo("4321-df23-214");
        assertThat(accountDTO.getMoney()).isEqualTo("30.00");
    }

    @Test
    void convertDtoToEntity() {
        AccountDTO accountDTO = AccountDTO.builder()
                .id(1L)
                .ban(true)
                .accountName("U1234-df23-214")
                .accountNumber("4321-df23-214")
                .money("30.00")
                .build();
        Account account = accountDtoConverter.convertDtoToEntity(accountDTO);
        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.isBan()).isEqualTo(true);
        assertThat(account.getAccountName()).isEqualTo("U1234-df23-214");
        assertThat(account.getAccountNumber()).isEqualTo("4321-df23-214");
        assertThat(account.getMoney()).isEqualTo(3000L);
    }
}