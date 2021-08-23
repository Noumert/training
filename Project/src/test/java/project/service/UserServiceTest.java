package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.repository.UserRepository;
import project.entity.MyUserDetails;
import project.entity.RoleType;
import project.entity.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsernameSuccess() {
        User user = User.builder()
                .id(1L)
                .email("ivan@gmail.com")
                .role(RoleType.ROLE_USER)
                .build();
        MyUserDetails myUserDetails = MyUserDetails.builder()
                .id(1L)
                .username("ivan@gmail.com")
                .authorities(Collections.singleton(new SimpleGrantedAuthority(RoleType.ROLE_USER.name())))
                .build();
        Mockito.when(userRepository.findByEmail("ivan@gmail.com")).thenReturn(Optional.of(user));

        assertThat(userService.loadUserByUsername("ivan@gmail.com")).isEqualTo(myUserDetails);
    }

    @Test
    void loadUserByUsernameUsernameNotFoundException() {
        Mockito.when(userRepository.findByEmail("ivan@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("ivan@gmail.com"));
    }

    @Test
    public void saveSuccess() {
        User user = User.builder()
                .id(1L)
                .build();
        Mockito.when(userRepository.save(any(User.class))).then(returnsFirstArg());

        assertThat(userService.save(user)).isEqualTo(user);
    }

    @Test
    public void saveException() {
        User user = User.builder()
                .id(1L)
                .build();
        Mockito.when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    void findAll() {
        User user1 = User.builder()
                .id(1L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .build();
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertThat(userService.findAll()).isEqualTo(users);
    }

    @Test
    void findByRole() {
        User user1 = User.builder()
                .id(1L)
                .role(RoleType.ROLE_USER)
                .build();
        User user2 = User.builder()
                .id(2L)
                .role(RoleType.ROLE_USER)
                .build();
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userRepository.findByRole(RoleType.ROLE_USER)).thenReturn(users);

        assertThat(userService.findByRole(RoleType.ROLE_USER)).isEqualTo(users);
    }

    @Test
    void setAccountNonLockedByUser() {
        User user = User.builder()
                .id(1L)
                .accountNonLocked(false)
                .build();
        Mockito.when(userRepository.save(any(User.class))).then(returnsFirstArg());

        assertThat(userService.setAccountNonLockedByUser(true, user).isAccountNonLocked()).isEqualTo(true);
    }

    @Test
    void findById() {
        User user = User.builder()
                .id(1L)
                .build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThat(userService.findById(1L)).isEqualTo(Optional.of(user));
    }
}