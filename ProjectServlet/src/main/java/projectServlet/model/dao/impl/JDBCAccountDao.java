package projectServlet.model.dao.impl;

import projectServlet.model.dao.AccountDao;
import projectServlet.model.dao.mapper.AccountMapper;
import projectServlet.model.dao.mapper.ObjectMapper;
import projectServlet.model.dao.mapper.UserMapper;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCAccountDao implements AccountDao {
    private Connection connection;

    public JDBCAccountDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Account account) {
        if (account.getId() == null) {
            try (PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO account (account_name, account_number ," +
                            " ban, money, user_id)" +
                            " VALUES (? ,? ,?, ? ,? )")) {
                ps.setString(1, account.getAccountName());
                ps.setString(2, account.getAccountNumber());
                ps.setBoolean(3, account.isBan());
                ps.setLong(4, account.getMoney());
                ps.setLong(5, account.getUser().getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement
                    ("update account set account_name=?, account_number=? ," +
                            " ban=?, money=?, user_id=? " +
                            "where account_id=?")) {
                ps.setString(1, account.getAccountName());
                ps.setString(2, account.getAccountNumber());
                ps.setBoolean(3, account.isBan());
                ps.setLong(4, account.getMoney());
                ps.setLong(5, account.getUser().getId());
                ps.setLong(6, account.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Account> findByUserId(Long userId) {
        Map<Long, User> cache = new HashMap<>();
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from account " +
                "left join user using (user_id) where user_id = ?")) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                account.setUser(userMapper.makeUnique(cache, user));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> findByUserId(Long userId, int page,int pageSize, String sortBy, boolean asc) {
        Map<Long, User> cache = new HashMap<>();
        List<Account> accounts = new ArrayList<>();

        String query = "select * from account " +
                "left join user using (user_id) where user_id = ? ";

        if(asc){
            query+=" order by " + sortBy + " asc limit ?,?";
        }else {
            query+=" order by " + sortBy + " desc limit ?,?";
        }

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, userId);
            int start=(page-1) * pageSize;
            ps.setInt(2, start);
            ps.setInt(3, (start+pageSize));

            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                account.setUser(userMapper.makeUnique(cache, user));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> findByAccountName(String accountName) {

        try (PreparedStatement ps = connection.prepareStatement("select * from account " +
                "left join user using (user_id) where account_name = ?")) {

            ps.setString(1, accountName);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();

            if (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                account.setUser(user);
                return Optional.of(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findFreeUserAccountsByUserId(Long userId) {
        Map<Long, User> cache = new HashMap<>();
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select a.*,u.* from account a " +
                "left join user u using (user_id) " +
                "left join credit_card c using (account_id) " +
                "where a.user_id = ? and c.credit_card_id is null")) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                account.setUser(userMapper.makeUnique(cache, user));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Account account) {

    }

    @Override
    public Optional<Account> findById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM account " +
                        "left join user using (user_id) WHERE account_id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();
            if (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                account.setUser(user);
                return Optional.of(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        Map<Long, User> cache = new HashMap<>();
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from account " +
                "left join user using (user_id)")) {

            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                account.setUser(userMapper.makeUnique(cache, user));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
