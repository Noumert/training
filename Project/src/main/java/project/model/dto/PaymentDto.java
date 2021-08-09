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
public class PaymentDto {
    private Long id;
    private String PaymentNumber;
    private Long money;
    private LocalDateTime dateTime;
    private StatusType status;
    private Account account;
    private String recipient;
}
