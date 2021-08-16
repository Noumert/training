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
        account.setAccountName(randomAccountName());
        account.setAccountNumber(randomAccountNumber());
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


    private String randomAccountNumber() {
        return UUID.randomUUID().toString();
    }

    private String randomAccountName() {
        return "U"+UUID.randomUUID();
    }

    private String randomFourDigits() {
        return String.valueOf((int) Math.floor(Math.random() * (AccountService.MAX_RANDOM - AccountService.MIN_RANDOM + 1) + AccountService.MIN_RANDOM));
    }

    public List<Account> findUserAccountsByUserId(Long userId){
        return accountRepository
                .findByUserId(userId);
    }

    public Page<Account> findUserAccountsByUserId(Long userId, Pageable pageable){
        return accountRepository
                .findByUserId(userId,pageable);
    }

    public List<Account> findUserAccountsByUserIdOrderByAccountName(Long userId){
        return accountRepository
                .findByUserIdOrderByAccountName(userId);
    }

    public List<Account> findUserAccountsByUserIdOrderByAccountNumber(Long userId){
        return accountRepository
                .findByUserIdOrderByAccountNumber(userId);
    }

    public List<Account> findUserAccountsByUserIdOrderByMoney(Long userId){
        return accountRepository
                .findByUserIdOrderByMoney(userId);
    }

    Optional<Account> findByAccountName(String accountName){
        return accountRepository
                .findByAccountName(accountName);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long accountId){
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

    public void setBanById(boolean ban, Account account) {
        account.setBan(ban);
        save(account);
    }

    @Transactional
    public void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest) throws NotFoundException {
        unbanAccountRequestService.setResolvedByRequest(resolved, unbanAccountRequest);
        this.setBanById(ban, unbanAccountRequest.getAccount());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE, rollbackFor = NotEnoughMoneyException.class)
    public void addMoneyById(Long money,@NotNull Account account) throws NotEnoughMoneyException {
        account.setMoney(account.getMoney() + money);
        save(account);
        if (account.getMoney() < 0) {
            throw new NotEnoughMoneyException("money can't be negative");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE, rollbackFor = NotEnoughMoneyException.class)
    public void decreaseMoneyById(Long money,@NotNull Account account) throws NotEnoughMoneyException{
        account.setMoney(account.getMoney() - money);
        save(account);
        if (account.getMoney() < 0) {
            throw new NotEnoughMoneyException("money can't be negative");
        }
    }
}
