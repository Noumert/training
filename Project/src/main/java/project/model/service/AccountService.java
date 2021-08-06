package project.model.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.entity.Account;
import project.model.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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


    public void saveNewAccount(Account account) throws NotFoundException {
        account.setMoney(START_MONEY_VALUE);
        account.setAccountName(generateAccountName());
        account.setAccountNumber(generateAccountNumber());
        account.setBan(false);
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
        return random()
                + random()
                + random()
                + random();
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
                + random()
                + random()
                + random()
                + random();
    }

    private String random() {
        return String.valueOf((int) Math.floor(Math.random() * (AccountService.MAX_RANDOM - AccountService.MIN_RANDOM + 1) + AccountService.MIN_RANDOM));
    }

    public List<Account> findCurrentUserAccounts() throws NotFoundException {
        return accountRepository
                .findByUserId(userService.getCurrentUser()
                        .getId());
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

    public List<Account> findFreeCurrentUserAccounts() throws NotFoundException {
        return accountRepository
                .findByUserId(userService.getCurrentUser().getId())
                .stream()
                .filter(account -> !creditCardService.findByAccountId(account.getId()).isPresent())
                .collect(Collectors.toList());
    }

    @Transactional
    public void setBanById(boolean ban,Long accountId){
        try {
            log.info("id {} ban {}",accountId,ban);
            accountRepository.setBanById(ban,accountId);
        } catch (Exception e){
            throw new RuntimeException();
        }

    }
}
