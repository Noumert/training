package project.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.Account;
import project.entity.CreditCard;
import project.repository.AccountRepository;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    CreditCardService creditCardService;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void saveSuccess() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        assertThat(accountService.save(account)).isEqualTo(account);
    }

    @Test
    public void saveException() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(accountRepository.save(any(Account.class))).thenThrow(RuntimeException.class);
        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> accountService.save(account));
    }

    @Test
    public void findFreeUserAccountsByUserId() {
        Account account1 = Account.builder()
                .id(1L)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .build();
        List<Account> accounts = Arrays.asList(account1, account2);
        Mockito.when(accountRepository.findByUserId(1L)).thenReturn(accounts);
        Mockito.when(creditCardService.findByAccountId(1L)).thenReturn(Optional.of(new CreditCard()));
        Mockito.when(creditCardService.findByAccountId(2L)).thenReturn(Optional.empty());
        List<Account> expected = Collections.singletonList(account2);
        assertThat(accountService.findFreeUserAccountsByUserId(1L)).isEqualTo(expected);
    }

    @Test
    public void setBanByAccount() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        assertThat(accountService.setBanByAccount(true,account).isBan()).isEqualTo(true);
    }

    @Test
    public void addMoneyById() {
        assertThat(false).isEqualTo(true);
    }

    @Test
    public void decreaseMoneyById() {
        assertThat(false).isEqualTo(true);
    }
}