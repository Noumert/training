package project.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.UserDao;
import projectServlet.model.entity.RoleType;
import projectServlet.model.entity.User;
import projectServlet.model.service.UserServiceImpl;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveSuccess() {
        User user = User.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createUserDao()).thenReturn(userDao);
        Mockito.doNothing().when(userDao).save(any(User.class));

        assertDoesNotThrow(()->userService.save(user));
    }

    @Test
    public void saveException() {
        User user = User.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createUserDao()).thenReturn(userDao);
        Mockito.doThrow(RuntimeException.class).when(userDao).save(any(User.class));

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
        Mockito.when(daoFactory.createUserDao()).thenReturn(userDao);
        Mockito.doReturn(users).when(userDao).findAll();

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
        Mockito.when(daoFactory.createUserDao()).thenReturn(userDao);
        Mockito.doReturn(users).when(userDao).findByRole(RoleType.ROLE_USER);

        assertThat(userService.findByRole(RoleType.ROLE_USER)).isEqualTo(users);
    }

    @Test
    void setAccountNonLockedByUser() {
        User user = User.builder()
                .id(1L)
                .accountNonLocked(false)
                .build();
        Mockito.when(daoFactory.createUserDao()).thenReturn(userDao);
        Mockito.doNothing().when(userDao).save(user);

        assertDoesNotThrow(()->userService.setAccountNonLockedByUser(true,user));
    }

    @Test
    void findById() {
        User user = User.builder()
                .id(1L)
                .build();
        Mockito.when(daoFactory.createUserDao()).thenReturn(userDao);
        Mockito.doReturn(Optional.of(user)).when(userDao).findById(1L);


        assertThat(userService.findById(1L)).isEqualTo(Optional.of(user));
    }
}