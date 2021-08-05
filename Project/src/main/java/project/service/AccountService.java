package project.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.entity.Account;
import project.entity.User;
import project.exceptions.DuplicatedNumberException;
import project.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    final static long START_MONEY_VALUE = 0L;
    final static int MIN_RANDOM = 1000;
    final static int MAX_RANDOM = 9999;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveNewAccount() throws NotFoundException, DuplicatedNumberException {
        User user = userService.getCurrentUser();

        try {
            accountRepository.save(Account.builder()
                    .money(START_MONEY_VALUE)
                    .accountName(generateAccountName())
                    .accountName(generateAccountNumber())
                    .ban(false)
                    .user(user)
                    .build());
        } catch (Exception e) {
            throw new DuplicatedNumberException("same number exist");
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
        return  random()
                + random()
                + random()
                + random();
    }

    private String generateAccountName() {
        List<String> accountNames = accountRepository.findAll().stream().map(Account::getAccountName).collect(Collectors.toList());
        String accountName = randomAccountNumber();
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


    @Transactional
    public List<Account> findCurrentUserAccounts() throws NotFoundException {
        return accountRepository
                .findByUserId(userService.getCurrentUser()
                        .getId());
    }

    Account findByAccountName(String accountName) throws NotFoundException {
        return accountRepository.findByAccountName(accountName).orElseThrow(()->new NotFoundException("no such account"));
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long accountId) throws NotFoundException {
        return accountRepository.findById(accountId).orElseThrow(()-> new NotFoundException("no such account"));
    }
}
