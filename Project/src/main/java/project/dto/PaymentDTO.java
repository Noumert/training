package project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentDTO {
    private Long id;
    private String paymentNumber;
    private String money;
    private String dateTime;
    private String status;
    private AccountDTO account;
    private String recipient;
}
