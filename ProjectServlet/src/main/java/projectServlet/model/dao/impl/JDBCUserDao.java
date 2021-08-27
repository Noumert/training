package projectServlet.model.dao.impl;

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
    public User findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM user WHERE id = ?")){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<User> userMapper = new UserMapper();
            if( rs.next() ){
                return userMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

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
                        " VALUES (? ,? ,?, ? ,? ,? )")){
            ps.setBoolean(1 , user.isAccountNonLocked());
            ps.setString(2 ,user.getEmail());
            ps.setString(3 , user.getFirstName());
            ps.setString(4 ,user.getLastName());
            ps.setString(5 , user.getPassword());
            ps.setString(6 ,user.getRole().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findByRole(RoleType roleType) {
        return null;
    }

    @Override
    public User setAccountNonLockedByUser(boolean accountNonLocked, User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long currentUserId) {
        return Optional.empty();
    }

    @Override
    public User authenticateUser(String username, String password) {
        Map<Long, User> users = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement("select * from user where email = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            ObjectMapper<User> userMapper = new UserMapper();

            User user = null;

            while (rs.next()) {
                user = userMapper
                        .extractFromResultSet(rs);
            }

            if (user == null) {
                return null;
            }

            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
