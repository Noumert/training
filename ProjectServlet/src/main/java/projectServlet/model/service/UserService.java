package projectServlet.model.service;


import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 23.08.2021.
 */
public interface UserService {

    void save(User user);

    List<User> findAll();

    List<User> findByRole(RoleType roleType);

    void setAccountNonLockedByUser(boolean accountNonLocked, User user);

    Optional<User> findById(Long currentUserId);

    Optional<User> authenticateUser(String username,String password);
}

