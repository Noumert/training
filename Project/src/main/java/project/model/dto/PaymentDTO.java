package project.model.dto;

import lombok.*;
import project.model.entity.Account;
import project.model.entity.StatusType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentDTO {
    private Long id;
    private String paymentNumber;
    private Long money;
    private LocalDateTime dateTime;
    private String status;
    private AccountDTO account;
    private String recipient;
}
