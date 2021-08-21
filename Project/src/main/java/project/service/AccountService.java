package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Account;
import project.exceptions.NotEnoughMoneyException;
import project.repository.AccountRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
