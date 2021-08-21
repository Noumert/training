package project.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.dto.*;
import project.entity.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AccountDtoConverterImplTest {
    @Autowired
    EntityDtoConverter<Account, AccountDTO> accountDtoConverter;

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