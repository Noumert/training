package project.dto;

import lombok.*;

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
    private Long money;
    private LocalDate expirationDate;
}
