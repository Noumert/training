package projectServlet.model.dao.impl;

import projectServlet.model.dao.UnbanAccountRequestDao;
import projectServlet.model.dao.mapper.AccountMapper;
import projectServlet.model.dao.mapper.ObjectMapper;
import projectServlet.model.dao.mapper.UnbanAccountRequestMapper;
import projectServlet.model.dao.mapper.UserMapper;
import projectServlet.model.entity.Account;
import projectServlet.model.entity.UnbanAccountRequest;
import projectServlet.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUnbanAccountRequestDao implements UnbanAccountRequestDao {
    private Connection connection;

    public JDBCUnbanAccountRequestDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(UnbanAccountRequest unbanAccountRequest) {
        if (unbanAccountRequest.getId() == null) {
            try (PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO unban_account_request (date_time, resolved ,account_id)" +
                            " VALUES (? ,? ,?)")) {
                ps.setTimestamp(1, Timestamp.valueOf(unbanAccountRequest.getDateTime()));
                ps.setBoolean(2,unbanAccountRequest.isResolved());
                ps.setLong(3, unbanAccountRequest.getAccount().getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement
                    ("update unban_account_request set date_time=?, resolved=? ,account_id=?" +
                            "where unban_account_request_id=?")) {
                ps.setTimestamp(1, Timestamp.valueOf(unbanAccountRequest.getDateTime()));
                ps.setBoolean(2,unbanAccountRequest.isResolved());
                ps.setLong(3, unbanAccountRequest.getAccount().getId());
                ps.setLong(4, unbanAccountRequest.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<UnbanAccountRequest> findByResolved(boolean resolved) {
        Map<Long, Account> cache = new HashMap<>();
        List<UnbanAccountRequest> unbanAccountRequests = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from unban_account_request " +
                "left join account using (account_id) where resolved = ?")) {

            ps.setBoolean(1, resolved);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<UnbanAccountRequest> unbanAccountRequestMapper = new UnbanAccountRequestMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                UnbanAccountRequest unbanAccountRequest = unbanAccountRequestMapper
                        .extractFromResultSet(rs);
                unbanAccountRequest.setAccount(accountMapper.makeUnique(cache, account));
                unbanAccountRequests.add(unbanAccountRequest);
            }
            return unbanAccountRequests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(UnbanAccountRequest unbanAccountRequest) {

    }

    @Override
    public Optional<UnbanAccountRequest> findById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement("select * from unban_account_request " +
                "left join account using (account_id) where unban_account_request_id=?")) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<UnbanAccountRequest> unbanAccountRequestMapper = new UnbanAccountRequestMapper();

            if (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                UnbanAccountRequest unbanAccountRequest = unbanAccountRequestMapper
                        .extractFromResultSet(rs);
                unbanAccountRequest.setAccount(account);
                return Optional.of(unbanAccountRequest);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<UnbanAccountRequest> findAll() {
        Map<Long, Account> cache = new HashMap<>();
        List<UnbanAccountRequest> unbanAccountRequests = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from unban_account_request " +
                "left join account using (account_id)")) {

            ResultSet rs = ps.executeQuery();

            ObjectMapper<Account> accountMapper = new AccountMapper();
            ObjectMapper<UnbanAccountRequest> unbanAccountRequestMapper = new UnbanAccountRequestMapper();

            while (rs.next()) {
                Account account = accountMapper
                        .extractFromResultSet(rs);
                UnbanAccountRequest unbanAccountRequest = unbanAccountRequestMapper
                        .extractFromResultSet(rs);
                unbanAccountRequest.setAccount(accountMapper.makeUnique(cache, account));
                unbanAccountRequests.add(unbanAccountRequest);
            }
            return unbanAccountRequests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UnbanAccountRequest unbanAccountRequest) {

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
