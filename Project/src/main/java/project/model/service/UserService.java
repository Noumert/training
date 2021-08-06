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

    public Optional<User> findByUserLogin(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("no such user"));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(RoleType role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    public UserDetails mapUserToUserDetails(User user) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return mapRolesToAuthorities(user.getRole());
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return user.isAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByRole(RoleType roleType) {
        return userRepository.findByRole(roleType);
    }

    @Transactional
    public void setBanById(boolean accountNonLocked, Long userId) {
        log.info("accountNonLocked {} userId {}",accountNonLocked,userId);
        userRepository.setBanById(accountNonLocked,userId);
    }
}

