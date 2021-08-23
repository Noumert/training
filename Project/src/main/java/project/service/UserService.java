package project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import project.entity.RoleType;
import project.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Noumert on 20.08.2021.
 */
public interface UserService extends UserDetailsService {

    User save(User user);

    List<User> findAll();

    List<User> findByRole(RoleType roleType);

    User setAccountNonLockedByUser(boolean accountNonLocked, User user);

    Optional<User> findById(Long currentUserId);
}

