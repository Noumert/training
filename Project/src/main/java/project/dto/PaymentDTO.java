package project.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Noumert on 13.08.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private String paymentNumber;
    private String money;
    private String dateTime;
    private String status;
    private AccountDTO account;
    private String recipient;
}
