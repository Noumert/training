package projectServlet.model.dao;


import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    void save(User user);

    List<User> findByRole(RoleType roleType);

    User setAccountNonLockedByUser(boolean accountNonLocked, User user);

    Optional<User> findById(Long currentUserId);

    User authenticateUser(String username,String password);
}
