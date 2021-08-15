package project.dto;

import lombok.*;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditCardDTO {
    private Long id;
    private String cardNumber;
    private String expirationDate;
}
