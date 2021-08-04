package project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.dto.UserDTO;
import project.entity.RoleType;
import project.entity.User;
import project.exceptions.DublicatedEmailException;
import project.repository.UserRepository;
//import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        UserDetails userDetails = mapUserToUserDetails(user);
        return userDetails;
    }

    public void saveNewUser(UserDTO userDto) throws DublicatedEmailException {
        //TODO inform the user about the replay email
        // TODO exception to endpoint
        try {
            userRepository.save(User
                    .builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(RoleType.ROLE_USER)
                    .build());
        } catch (Exception ex) {
            throw new DublicatedEmailException("Same email exist");
        }

    }

    public Optional<User> findByUserLogin(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);
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
                return true;
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
}

