package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.entity.MyUserDetails;
import project.entity.RoleType;
import project.entity.User;
import project.exceptions.DuplicatedEmailException;
import project.repository.UserRepository;

import java.util.*;

/**
 * Created by Noumert on 13.08.2021.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return mapUserToUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password.")));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(RoleType role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    private MyUserDetails mapUserToUserDetails(User user) {
        return MyUserDetails.builder()
                .authorities(mapRolesToAuthorities(user.getRole()))
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountNonLocked(user.isAccountNonLocked())
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByRole(RoleType roleType) {
        return userRepository.findByRole(roleType);
    }

    @Override
    public User setAccountNonLockedByUser(boolean accountNonLocked, User user) {
        user.setAccountNonLocked(accountNonLocked);
        return save(user);
    }

    @Override
    public Optional<User> findById(Long currentUserId) {
        return userRepository.findById(currentUserId);
    }
}

