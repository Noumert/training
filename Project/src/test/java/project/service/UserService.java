package project.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.entity.MyUserDetails;
import project.entity.RoleType;
import project.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User save(User user);

    Optional<User> findByUserLogin(String email);

    Collection<GrantedAuthority> mapRolesToAuthorities(RoleType role);

    MyUserDetails mapUserToUserDetails(User user);

    List<User> findAll();

    List<User> findByRole(RoleType roleType);

    void setAccountNonLockedByUser(boolean accountNonLocked, User user);

    Optional<User> findById(Long currentUserId);
}
