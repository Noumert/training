package project.model.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.model.entity.MyUserDetails;
import project.model.entity.RoleType;
import project.model.entity.User;
import project.exceptions.DuplicatedEmailException;
import project.model.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return mapUserToUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password.")));
    }

    public void saveNewUser(User user) throws DuplicatedEmailException {
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new DuplicatedEmailException("Same email exist");
        }

    }

    public void save(User user) throws DuplicatedEmailException {
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new DuplicatedEmailException("Same email exist");
        }

    }

    public Optional<User> findByUserLogin(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("no such user"));
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

    public void setAccountNonLockedById(boolean accountNonLocked, Long userId) {
        userRepository.setBanById(accountNonLocked,userId);
    }
}

