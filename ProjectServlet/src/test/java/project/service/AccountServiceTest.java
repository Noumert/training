package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import projectServlet.model.dao.AccountDao;
import projectServlet.model.dao.CreditCardDao;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.CreditCard;
import projectServlet.model.entity.Payment;
import projectServlet.model.service.AccountServiceImpl;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void saveSuccess() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doNothing().when(accountDao).save(any(Account.class));

        assertDoesNotThrow(()->accountService.save(account));
    }

    @Test
    public void saveException() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doThrow(RuntimeException.class).when(accountDao).save(any(Account.class));

        assertThrows(RuntimeException.class,()->accountService.save(account));
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
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doReturn(accounts).when(accountDao).findFreeUserAccountsByUserId(1L);

        assertThat(accountService.findFreeUserAccountsByUserId(1L)).isEqualTo(accounts);
    }

    @Test
    public void setBanByAccount() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doNothing().when(accountDao).save(any(Account.class));

        assertDoesNotThrow(()->accountService.setBanByAccount(true,account));
    }

    @Test
    void findByUserIdPage() {
        Account account1 = Account.builder()
                .id(1L)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .build();
        List<Account> accounts = Arrays.asList(account1, account2);
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doReturn(accounts).when(accountDao).findByUserId(1L,1,5,"payment_id",true);

        assertThat(accountService.findByUserId(1L,1,5,"payment_id",true)).isEqualTo(accounts);
    }

    @Test
    void findByUserId() {
        Account account1 = Account.builder()
                .id(1L)
                .build();
        Account account2 = Account.builder()
                .id(2L)
                .build();
        List<Account> accounts = Arrays.asList(account1, account2);
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doReturn(accounts).when(accountDao).findByUserId(1L);

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
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doReturn(accounts).when(accountDao).findAll();

        assertThat(accountService.findAll()).isEqualTo(accounts);
    }

    @Test
    void findById() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createAccountDao()).thenReturn(accountDao);
        Mockito.doReturn(Optional.of(account)).when(accountDao).findById(1L);

        assertThat(accountService.findById(1L)).isEqualTo(Optional.of(account));
    }

}