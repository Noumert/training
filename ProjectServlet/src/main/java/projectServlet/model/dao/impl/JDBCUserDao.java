package projectServlet.model.dao.impl;

import projectServlet.config.PasswordEncoder;
import projectServlet.model.dao.UserDao;
import projectServlet.model.dao.mapper.ObjectMapper;
import projectServlet.model.dao.mapper.UserMapper;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(User entity) {

    }

    @Override
    public Optional<User> findById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<User> userMapper = new UserMapper();
            if (rs.next()) {
                return Optional.of(userMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        final String query = "select * from user";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            ObjectMapper<User> studentMapper = new UserMapper();

            while (rs.next()) {
                User user = studentMapper
                        .extractFromResultSet(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {

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
    public void save(User user) {
        try (PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO user (account_non_locked, email, first_name , last_name, password, role)" +
                        " VALUES (? ,? ,?, ? ,? ,? )")) {
            ps.setBoolean(1, user.isAccountNonLocked());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findByRole(RoleType roleType) {
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from user where role = ?")) {

            ps.setString(1, roleType.name());
            ResultSet rs = ps.executeQuery();

            ObjectMapper<User> userMapper = new UserMapper();

            while (rs.next()) {
                User user = userMapper
                        .extractFromResultSet(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User authenticateUser(String username, String password) {
        Map<Long, User> users = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from user where email = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            User user = null;
            ObjectMapper<User> userMapper = new UserMapper();
            while (rs.next()) {
                user = userMapper
                        .extractFromResultSet(rs);
            }

            if (user == null) {
                return null;
            }

            if (user.getEmail().equals(username)
                    && PasswordEncoder.passwordEncoder().matches(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
