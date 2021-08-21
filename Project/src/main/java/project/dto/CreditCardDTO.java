package project.dto;

import lombok.*;


import java.time.LocalDate;

/**
 * Created by Noumert on 13.08.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDTO {
    private Long id;
    private String cardNumber;
    private String expirationDate;
}
