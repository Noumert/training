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

@Service
public class UserServiceImpl implements UserDetailsService,UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return mapUserToUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password.")));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUserLogin(String email) {
        return userRepository.findByEmail(email);
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByRole(RoleType roleType) {
        return userRepository.findByRole(roleType);
    }

    public void setAccountNonLockedByUser(boolean accountNonLocked, User user) {
        user.setAccountNonLocked(accountNonLocked);
        save(user);
    }

    public Optional<User> findById(Long currentUserId) {
        return userRepository.findById(currentUserId);
    }
}

