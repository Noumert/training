package projectServlet.model.dao.impl;

import projectServlet.model.dao.CreditCardDao;
import projectServlet.model.dao.mapper.AccountMapper;
import projectServlet.model.dao.mapper.CreditCardMapper;
import projectServlet.model.dao.mapper.ObjectMapper;
import projectServlet.model.dao.mapper.UserMapper;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.CreditCard;
import projectServlet.model.entity.User;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JDBCCreditCardDao implements CreditCardDao {
    private Connection connection;

    public JDBCCreditCardDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(CreditCard creditCard) {
        if (creditCard.getId() == null) {
            try (PreparedStatement ps = connection.prepareStatement
                    ("insert into credit_card (card_number, expiration_date ," +
                            " account_id, user_id)" +
                            " values (? ,? ,?, ?)")) {
                ps.setString(1, creditCard.getCardNumber());
                ps.setDate(2, new Date(Timestamp.valueOf(
                        creditCard.getExpirationDate()
                                .atStartOfDay()).getTime()));
                ps.setLong(3, creditCard.getAccount().getId());
                ps.setLong(4, creditCard.getUser().getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement
                    ("update credit_card set card_number=?, expiration_date=? ," +
                            " account_id=?, user_id=? " +
                            " where credit_card_id=?")) {
                ps.setString(1, creditCard.getCardNumber());
                ps.setDate(2, new Date(Timestamp.valueOf(
                        creditCard.getExpirationDate()
                                .atStartOfDay()).getTime()));
                ps.setLong(3, creditCard.getAccount().getId());
                ps.setLong(4, creditCard.getUser().getId());
                ps.setLong(5, creditCard.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void create(CreditCard entity) {

    }

    @Override
    public Optional<CreditCard> findById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement
                ("select * from credit_card c " +
                        "left join account a on a.account_id=c.account_id " +
                        " left join user u on u.user_id=c.user_id " +
                        " where c.credit_card_id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();
            ObjectMapper<CreditCard> creditCardMapper = new CreditCardMapper();
            if (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                CreditCard creditCard = creditCardMapper
                        .extractFromResultSet(rs);
                creditCard.setAccount(account);
                creditCard.setUser(user);
                return Optional.of(creditCard);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<CreditCard> findAll() {
        Map<Long, User> cacheUser = new HashMap<>();
        Map<Long, Account> cacheAccount = new HashMap<>();
        List<CreditCard> creditCards = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from credit_card c " +
                "left join account a on a.account_id=c.account_id " +
                " left join user u on u.user_id=c.user_id ")) {

            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();
            ObjectMapper<CreditCard> creditCardMapper = new CreditCardMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                CreditCard creditCard = creditCardMapper
                        .extractFromResultSet(rs);
                creditCard.setAccount(accountMapper.makeUnique(cacheAccount,account));
                creditCard.setUser(userMapper.makeUnique(cacheUser,user));
                creditCards.add(creditCard);
            }
            return creditCards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CreditCard entity) {

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

    @Override
    public List<CreditCard> findByUserId(Long userId) {
        Map<Long, User> cacheUser = new HashMap<>();
        Map<Long, Account> cacheAccount = new HashMap<>();
        List<CreditCard> creditCards = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from credit_card c " +
                "left join account a on a.account_id=c.account_id " +
                " left join user u on u.user_id=c.user_id " +
                " where c.user_id=?")) {

            ps.setLong(1,userId);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();
            ObjectMapper<CreditCard> creditCardMapper = new CreditCardMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                CreditCard creditCard = creditCardMapper
                        .extractFromResultSet(rs);
                creditCard.setAccount(accountMapper.makeUnique(cacheAccount,account));
                creditCard.setUser(userMapper.makeUnique(cacheUser,user));
                creditCards.add(creditCard);
            }
            return creditCards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CreditCard> findByAccountId(Long accountId) {
        try (PreparedStatement ps = connection.prepareStatement("select * from credit_card c " +
                "left join account a on a.account_id=c.account_id " +
                " left join user u on u.user_id=c.user_id " +
                " where a.account_id=?")) {

            ps.setLong(1,accountId);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<User> userMapper = new UserMapper();
            ObjectMapper<CreditCard> creditCardMapper = new CreditCardMapper();

            if (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                User user = userMapper
                        .extractFromResultSet(rs);
                CreditCard creditCard = creditCardMapper
                        .extractFromResultSet(rs);
                creditCard.setAccount(account);
                creditCard.setUser(user);
                return Optional.of(creditCard);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
