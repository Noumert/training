package project.service;

import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.Account;
import project.entity.CreditCard;
import project.exceptions.NotEnoughMoneyException;
import project.repository.AccountRepository;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        assertThrows(RuntimeException.class, () -> accountService.save(account));
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
        Mockito.when(accountRepository.findFreeUserAccountsByUserId(1L)).thenReturn(accounts);

        assertThat(accountService.findFreeUserAccountsByUserId(1L)).isEqualTo(accounts);
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
    public void addMoneyByIdSuccess() throws NotFoundException {
        Account account = Account.builder()
                .id(1L)
                .money(0L)
                .build();
        Mockito.when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThat(accountService.addMoneyById(300L,1L).getMoney()).isEqualTo(300L);
    }

    @Test
    public void addMoneyByIdNotFoundException() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.addMoneyById(300L,1L));
    }

    @Test
    public void decreaseMoneyByIdSuccess() throws NotEnoughMoneyException, NotFoundException {
        Account account = Account.builder()
                .id(1L)
                .money(300L)
                .build();
        Mockito.when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThat(accountService.decreaseMoneyById(300L,1L).getMoney()).isEqualTo(0L);
    }

    @Test
    public void decreaseMoneyByIdNotEnoughMoneyException(){
        Account account = Account.builder()
                .id(1L)
                .money(200L)
                .build();
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(NotEnoughMoneyException.class, () -> accountService.decreaseMoneyById(300L,1L));
    }

    @Test
    public void decreaseMoneyByIdNotFoundException(){
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.decreaseMoneyById(100L,1L));
    }
}