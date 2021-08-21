package project.dto;

import lombok.*;


import java.time.LocalDate;

/**
 * Created by Noumert on 13.08.2021.
 */
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
