package project.dto;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import project.entity.Roles;
import project.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String matchingPassword;
    private String email;
}

