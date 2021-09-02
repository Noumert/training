package projectServlet.model.service;

import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.dao.AccountDao;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.UserDao;
import projectServlet.model.dao.impl.ConnectionPoolHolder;
import projectServlet.model.dao.impl.JDBCAccountDao;
import projectServlet.model.entity.Account;

import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 30.08.2021.
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

    @Override
    public List<Account> findByUserId(Long userId,int page,int pageSize,String sortBy,boolean asc) {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findByUserId(userId,page,pageSize,sortBy,asc);
        }
    }

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
    public void addMoneyById(Long money, @NotNull Long accountId) throws NotFoundException {
        try (Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
             AccountDao dao = new JDBCAccountDao(connection)) {
            connection.setAutoCommit(false);
            Account accountDB = dao.findById(accountId).orElseThrow(() -> new NotFoundException("no such account"));
            accountDB.setMoney(accountDB.getMoney() + money);
            dao.save(accountDB);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
