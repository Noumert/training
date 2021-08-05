package project.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCardDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Long id;
    private String cardNumber;
    private boolean active;
    private Long money;
    private LocalDate expirationDate;
}
