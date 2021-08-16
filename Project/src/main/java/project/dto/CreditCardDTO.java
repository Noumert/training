package project.dto;

import lombok.*;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditCardDTO {
    private Long id;
    private String cardNumber;
    private String expirationDate;
}
