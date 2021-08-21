package project.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.entity.Account;
import project.exceptions.NotEnoughMoneyException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface AccountService {

    Account save(Account account);

    List<Account> findByUserId(Long userId);

    Page<Account> findByUserId(Long userId, Pageable pageable);

    List<Account> findAll();

    Optional<Account> findById(Long accountId);

    List<Account> findFreeUserAccountsByUserId(Long userId);

    Account setBanByAccount(boolean ban, Account account);

    Account addMoneyById(Long money, @NotNull Long accountId) throws NotFoundException;

    Account decreaseMoneyById(Long money, @NotNull Long accountId) throws NotEnoughMoneyException, NotFoundException;
}
