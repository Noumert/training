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
import project.entity.UnbanAccountRequest;
import project.exceptions.NotEnoughMoneyException;
import project.repository.AccountRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private UnbanAccountRequestService unbanAccountRequestService;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findByUserId(Long userId) {
        return accountRepository
                .findByUserId(userId);
    }

    public Page<Account> findByUserId(Long userId, Pageable pageable) {
        return accountRepository
                .findByUserId(userId, pageable);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public List<Account> findFreeUserAccountsByUserId(Long userId) {
        return accountRepository
                .findByUserId(userId)
                .stream()
                .filter(account -> !creditCardService.findByAccountId(account.getId()).isPresent())
                .collect(Collectors.toList());
    }

    public Account setBanByAccount(boolean ban, Account account) {
        account.setBan(ban);
        return save(account);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE)
    public Account addMoneyById(Long money, @NotNull Long accountId) throws NotFoundException {
        Account accountDB = findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
        accountDB.setMoney(accountDB.getMoney() + money);
        return save(accountDB);
    }

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