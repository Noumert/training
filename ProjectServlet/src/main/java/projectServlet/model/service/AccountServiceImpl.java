package projectServlet.model.service;

import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.dao.AccountDao;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.UserDao;
import projectServlet.model.entity.Account;

import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 13.08.2021.
 */

public class AccountServiceImpl implements AccountService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public void save(Account account) {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.save(account);
        }
    }

    @Override
    public List<Account> findByUserId(Long userId) {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findByUserId(userId);
        }
    }

//    @Override
//    public Page<Account> findByUserId(Long userId, Pageable pageable) {
//        return accountRepository
//                .findByUserId(userId, pageable);
//    }

    @Override
    public List<Account> findAll() {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findAll();
        }
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findById(accountId);
        }
    }

    @Override
    public List<Account> findFreeUserAccountsByUserId(Long userId) {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findFreeUserAccountsByUserId(userId);
        }
    }

    @Override
    public void setBanByAccount(boolean ban, Account account) {
        account.setBan(ban);
        save(account);
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW,
//            isolation = Isolation.SERIALIZABLE)
    //TODO transactional
    public void addMoneyById(Long money, @NotNull Long accountId) throws NotFoundException {

        Account accountDB = findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
        accountDB.setMoney(accountDB.getMoney() + money);
        save(accountDB);
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW,
//            isolation = Isolation.SERIALIZABLE, rollbackFor = {NotEnoughMoneyException.class})
    //TODO transactional
    public void decreaseMoneyById(Long money, @NotNull Long accountId) throws NotEnoughMoneyException, NotFoundException {
        Account accountDB = findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
        accountDB.setMoney(accountDB.getMoney() - money);
        if (accountDB.getMoney() < 0) {
            throw new NotEnoughMoneyException("money can't be negative");
        }
        save(accountDB);
    }
}
