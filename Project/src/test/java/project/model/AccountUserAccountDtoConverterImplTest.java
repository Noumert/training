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
class AccountUserAccountDtoConverterImplTest {
    @Autowired
    EntityDtoConverter<Account, UserAccountDTO> accountUserAccountDtoConverter;

    @Test
    void convertEntityToDto() {
        User user = User.builder()
                .email("test@gamail.com")
                .firstName("TestName")
                .lastName("TestLastName")
                .build();
        Account account = Account.builder()
                .id(1L)
                .ban(true)
                .accountName("U1234-df23-214")
                .accountNumber("4321-df23-214")
                .money(3000L)
                .user(user)
                .build();
        UserAccountDTO userAccountDTO = accountUserAccountDtoConverter.convertEntityToDto(account);
        assertThat(userAccountDTO.getId()).isEqualTo(1L);
        assertThat(userAccountDTO.isBan()).isEqualTo(true);
        assertThat(userAccountDTO.getAccountName()).isEqualTo("U1234-df23-214");
        assertThat(userAccountDTO.getAccountNumber()).isEqualTo("4321-df23-214");
        assertThat(userAccountDTO.getMoney()).isEqualTo("30.00");
        assertThat(userAccountDTO.getEmail()).isEqualTo("test@gamail.com");
        assertThat(userAccountDTO.getFirstName()).isEqualTo("TestName");
        assertThat(userAccountDTO.getLastName()).isEqualTo("TestLastName");
    }

    @Test
    void convertDtoToEntity() {
        UserAccountDTO userAccountDTO = UserAccountDTO.builder()
            .accountNumber("4321-df23-214")
            .accountName("U1234-df23-214")
            .money("30.00")
            .id(1L)
            .ban(true)
            .build();
        Account account = accountUserAccountDtoConverter.convertDtoToEntity(userAccountDTO);
        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.isBan()).isEqualTo(true);
        assertThat(account.getAccountName()).isEqualTo("U1234-df23-214");
        assertThat(account.getAccountNumber()).isEqualTo("4321-df23-214");
        assertThat(account.getMoney()).isEqualTo(3000L);
    }
}