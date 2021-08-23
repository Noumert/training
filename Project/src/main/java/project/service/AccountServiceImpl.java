package project.service;

import javassist.NotFoundException;
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

/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findByUserId(Long userId) {
        return accountRepository
                .findByUserId(userId);
    }

    @Override
    public Page<Account> findByUserId(Long userId, Pageable pageable) {
        return accountRepository
                .findByUserId(userId, pageable);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public List<Account> findFreeUserAccountsByUserId(Long userId) {
        return accountRepository.findFreeUserAccountsByUserId(userId);
    }

    @Override
    public Account setBanByAccount(boolean ban, Account account) {
        account.setBan(ban);
        return save(account);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE)
    public Account addMoneyById(Long money, @NotNull Long accountId) throws NotFoundException {
        Account accountDB = findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
        accountDB.setMoney(accountDB.getMoney() + money);
        return save(accountDB);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE, rollbackFor = {NotEnoughMoneyException.class})
    public Account decreaseMoneyById(Long money, @NotNull Long accountId) throws NotEnoughMoneyException, NotFoundException {
        Account accountDB = findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
        accountDB.setMoney(accountDB.getMoney() - money);
        if (accountDB.getMoney() < 0) {
            throw new NotEnoughMoneyException("money can't be negative");
        }
        return save(accountDB);
    }
}
