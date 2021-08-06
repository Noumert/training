package project.model.dto;

import lombok.*;
import project.model.entity.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UnbanAccountRequestDTO {
    private Long id;
    private LocalDateTime dateTime;
    private boolean resolved;
    private AccountDTO account;
}
