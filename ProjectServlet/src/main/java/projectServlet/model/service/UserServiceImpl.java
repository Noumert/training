package projectServlet.model.service;

import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.UserDao;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 23.08.2021.
 */

public class UserServiceImpl implements UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public Optional<User> authenticateUser(String username,String password)
    {
        try (UserDao dao = daoFactory.createUserDao()) {
            return Optional.ofNullable(dao.authenticateUser(username,password));
        }
    }

    @Override
    public void save(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.save(user);
        }
    }

    @Override
    public List<User> findAll() {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAll();
        }
    }

    @Override
    public List<User> findByRole(RoleType roleType) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findByRole(roleType);
        }
    }

    @Override
    public void setAccountNonLockedByUser(boolean accountNonLocked, User user) {
        user.setAccountNonLocked(accountNonLocked);
        save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(userId);
        }
    }
}

