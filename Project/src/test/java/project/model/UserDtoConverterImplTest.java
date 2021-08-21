package project.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.dto.*;
import project.entity.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDtoConverterImplTest {
    @Autowired
    EntityDtoConverter<User, UserDTO> userDtoConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void convertEntityToDto() {
        User user = User
                .builder()
                .id(1L)
                .firstName("TestName")
                .lastName("TestLastName")
                .email("test@gmail.com")
                .password("password")
                .accountNonLocked(true)
                .build();
        UserDTO userDTO = userDtoConverter.convertEntityToDto(user);
        assertThat(userDTO.getId()).isEqualTo(1L);
        assertThat(userDTO.getFirstName()).isEqualTo("TestName");
        assertThat(userDTO.getLastName()).isEqualTo("TestLastName");
        assertThat(userDTO.getEmail()).isEqualTo("test@gmail.com");
        assertThat(userDTO.getPassword()).isEqualTo("password");
        assertThat(userDTO.isAccountNonLocked()).isEqualTo(true);
    }

    @Test
    void convertDtoToEntity() {
        UserDTO userDTO = UserDTO
                .builder()
                .id(1L)
                .firstName("TestName")
                .lastName("TestLastName")
                .email("test@gmail.com")
                .password("password")
                .accountNonLocked(true)
                .build();
        User user = userDtoConverter.convertDtoToEntity(userDTO);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("TestName");
        assertThat(user.getLastName()).isEqualTo("TestLastName");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(passwordEncoder.matches("password",user.getPassword())).isEqualTo(true);
        assertThat(user.isAccountNonLocked()).isEqualTo(true);
    }
}