package projectServlet.model.dao.impl;

import projectServlet.model.dao.PaymentDao;
import projectServlet.model.dao.mapper.AccountMapper;
import projectServlet.model.dao.mapper.ObjectMapper;
import projectServlet.model.dao.mapper.PaymentMapper;
import projectServlet.model.dao.mapper.UserMapper;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCPaymentDao implements PaymentDao {
    private Connection connection;

    public JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Payment payment) {
        if (payment.getId() == null) {
            try (PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO payment (payment_number, date_time ," +
                            " recipient,status,payment_money,account_id)" +
                            " VALUES (? ,? ,? ,? ,? ,?)")) {
                ps.setString(1, payment.getPaymentNumber());
                ps.setTimestamp(2, Timestamp.valueOf(payment.getDateTime()));
                ps.setString(3, payment.getRecipient());
                ps.setString(4, payment.getStatus().name());
                ps.setLong(5, payment.getMoney());
                ps.setLong(6, payment.getAccount().getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement
                    ("update payment set payment_number=?, date_time=? ," +
                            " recipient=?, status=?,payment_money=?,account_id=? " +
                            " where payment_id=?")) {
                ps.setString(1, payment.getPaymentNumber());
                ps.setTimestamp(2, Timestamp.valueOf(payment.getDateTime()));
                ps.setString(3, payment.getRecipient());
                ps.setString(4, payment.getStatus().name());
                ps.setLong(5, payment.getMoney());
                ps.setLong(6, payment.getAccount().getId());
                ps.setLong(7, payment.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void create(Payment entity) {

    }

    @Override
    public Optional<Payment> findById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement
                ("select * from payment " +
                        " left join account using (account_id) " +
                        " where payment_id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<Payment> paymentMapper = new PaymentMapper();
            if (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                Payment payment = paymentMapper
                        .extractFromResultSet(rs);
                payment.setAccount(account);
                return Optional.of(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Payment> findAll() {
        Map<Long, Account> cache = new HashMap<>();
        List<Payment> payments = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from payment " +
                " left join account using (account_id) ")) {

            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<Payment> paymentMapper = new PaymentMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                Payment payment = paymentMapper
                        .extractFromResultSet(rs);
                payment.setAccount(accountMapper.makeUnique(cache,account));
                payments.add(payment);
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Payment entity) {

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
    public List<Payment> findByUserId(Long userId) {
        Map<Long, Account> cache = new HashMap<>();
        List<Payment> payments = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select p.*,a.* from payment p " +
                " left join account a using (account_id) " +
                " left join user u using (user_id) " +
                " where user_id = ?")) {

            ps.setLong(1,userId);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<Payment> paymentMapper = new PaymentMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                Payment payment = paymentMapper
                        .extractFromResultSet(rs);
                payment.setAccount(accountMapper.makeUnique(cache,account));
                payments.add(payment);
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
