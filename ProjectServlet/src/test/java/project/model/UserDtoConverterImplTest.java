package project.model;

import org.junit.jupiter.api.Test;


import projectServlet.config.PasswordEncoder;
import projectServlet.model.converters.EntityDtoConverter;
import projectServlet.model.converters.UserDtoConverterImpl;
import projectServlet.model.dto.UserDTO;
import projectServlet.model.entity.User;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserDtoConverterImplTest {
    EntityDtoConverter<User, UserDTO> userDtoConverter = new UserDtoConverterImpl();

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
        assertThat(PasswordEncoder.passwordEncoder().matches("password",user.getPassword())).isEqualTo(true);
        assertThat(user.isAccountNonLocked()).isEqualTo(true);
    }
}