package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.UserDTO;
import project.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 21.08.2021.
 */
@Component
public class UserDtoConverterImpl implements EntityDtoConverter<User, UserDTO>{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO convertEntityToDto(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .accountNonLocked(user.isAccountNonLocked())
                .build();
    }

    @Override
    public User convertDtoToEntity(UserDTO userDTO) {
        return User
                .builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .accountNonLocked(userDTO.isAccountNonLocked())
                .build();
    }

    @Override
    public List<UserDTO> convertEntityListToDtoList(List<User> users) {
        return users.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<User> convertDtoListToPaymentList(List<UserDTO> userDTOS) {
        return userDTOS.stream().map(this::convertDtoToEntity).collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> convertEntityPageToDtoPage(Page<User> users) {
        return users.map(this::convertEntityToDto);
    }

    @Override
    public Page<User> convertDtoPageToEntityPage(Page<UserDTO> userDTOS) {
        return userDTOS.map(this::convertDtoToEntity);
    }
}
