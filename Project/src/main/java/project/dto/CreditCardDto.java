package project.dto;

import lombok.*;
import project.entity.Account;
import project.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditCardDto {
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private Long money;
}
