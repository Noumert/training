package project.model.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.exceptions.NotEnoughMoneyException;
import project.model.entity.Account;
import project.model.entity.UnbanAccountRequest;
import project.model.repository.AccountRepository;


import java.util.List;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Slf4j
@Transactional
@Service
public class AccountService {
    final static long START_MONEY_VALUE = 0L;
    final static int MIN_RANDOM = 1000;
    final static int MAX_RANDOM = 9999;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private UnbanAccountRequestService unbanAccountRequestService;

    public void saveNewAccount(Account account) throws NotFoundException {
        account.setMoney(START_MONEY_VALUE);
        account.setAccountName(generateAccountName());
        account.setAccountNumber(generateAccountNumber());
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("problem with save");
        }
    }

    public void save(Account account) {
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("problem with save");
        }
    }

    private String generateAccountNumber() {
        List<String> accountNumbers = accountRepository.findAll().stream().map(Account::getAccountNumber).collect(Collectors.toList());
        String accountNumber = randomAccountNumber();
        while (accountNumbers.contains(accountNumber)) {
            accountNumber = randomAccountNumber();
        }
        return accountNumber;
    }

    private String randomAccountNumber() {
        return randomFourDigits()
                + randomFourDigits()
                + randomFourDigits()
                + randomFourDigits();
    }

    private String generateAccountName() {
        List<String> accountNames = accountRepository.findAll().stream().map(Account::getAccountName).collect(Collectors.toList());
        String accountName = randomAccountName();
        while (accountNames.contains(accountName)) {
            accountName = randomAccountNumber();
        }
        return accountName;
    }

    private String randomAccountName() {
        return "U"
                + randomFourDigits()
                + randomFourDigits()
                + randomFourDigits()
                + randomFourDigits();
    }

    private String randomFourDigits() {
        return String.valueOf((int) Math.floor(Math.random() * (AccountService.MAX_RANDOM - AccountService.MIN_RANDOM + 1) + AccountService.MIN_RANDOM));
    }

    public List<Account> findUserAccountsByUserId(Long userId) throws NotFoundException {
        return accountRepository
                .findByUserId(userId);
    }

    public List<Account> findUserAccountsByUserIdOrderByAccountName(Long userId) throws NotFoundException {
        return accountRepository
                .findByUserIdOrderByAccountName(userId);
    }

    public List<Account> findUserAccountsByUserIdOrderByAccountNumber(Long userId) throws NotFoundException {
        return accountRepository
                .findByUserIdOrderByAccountNumber(userId);
    }

    public List<Account> findUserAccountsByUserIdOrderByMoney(Long userId) throws NotFoundException {
        return accountRepository
                .findByUserIdOrderByMoney(userId);
    }

    Account findByAccountName(String accountName) throws NotFoundException {
        return accountRepository.findByAccountName(accountName).orElseThrow(() -> new NotFoundException("no such account"));
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long accountId) throws NotFoundException {
        return accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
    }

    @Transactional
    public List<Account> findFreeUserAccountsByUserId(Long userId) throws NotFoundException {
        return accountRepository
                .findByUserId(userId)
                .stream()
                .filter(account -> !creditCardService.findByAccountId(account.getId()).isPresent())
                .collect(Collectors.toList());
    }

    public void setBanById(boolean ban, Long accountId) {
        accountRepository.setBanById(ban, accountId);
    }

    @Transactional
    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest) throws NotFoundException {
        unbanAccountRequestService.setResolvedById(resolved, unbanAccountRequest.getId());
        this.setBanById(ban, unbanAccountRequest.getAccount().getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE, rollbackFor = NotEnoughMoneyException.class)
    public void addMoneyById(Long money, Long accountId) throws NotEnoughMoneyException, NotFoundException {
        Account account = this.findById(accountId);
        account.setMoney(account.getMoney() + money);
        save(account);
        if (this.findById(accountId).getMoney() < 0) {
            throw new NotEnoughMoneyException("money can't be negative");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE, rollbackFor = NotEnoughMoneyException.class)
    public void decreaseMoneyById(Long money, Long accountId) throws NotEnoughMoneyException,
            NotFoundException {
        Account account = this.findById(accountId);
        account.setMoney(account.getMoney() - money);
        save(account);
        if (this.findById(accountId).getMoney() < 0) {
            throw new NotEnoughMoneyException("money can't be negative");
        }
    }
}
