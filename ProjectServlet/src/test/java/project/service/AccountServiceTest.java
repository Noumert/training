package project.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.entity.Account;
import project.exceptions.NotEnoughMoneyException;
import project.repository.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

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

    @Test
    void findByUserIdPage() {
        Account account1 = Account.builder()
                .id(1L)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .build();
        Page<Account> accounts = new PageImpl<>(Arrays.asList(account1,account2));
        Pageable pageable = PageRequest.of(1,2);
        Mockito.when(accountRepository.findByUserId(1L,pageable)).thenReturn(accounts);

        assertThat(accountService.findByUserId(1L,pageable)).isEqualTo(accounts);
    }

    @Test
    void findByUserId() {
        Account account1 = Account.builder()
                .id(1L)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .build();
        List<Account> accounts = Arrays.asList(account1,account2);
        Mockito.when(accountRepository.findByUserId(1L)).thenReturn(accounts);

        assertThat(accountService.findByUserId(1L)).isEqualTo(accounts);
    }

    @Test
    void findAll() {
        Account account1 = Account.builder()
                .id(1L)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .build();
        List<Account> accounts = Arrays.asList(account1,account2);
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        assertThat(accountService.findAll()).isEqualTo(accounts);
    }

    @Test
    void findById() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThat(accountService.findById(1L)).isEqualTo(Optional.of(account));
    }

}