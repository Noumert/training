package project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UnbanAccountRequestDTO {
    private Long id;
    private String dateTime;
    private boolean resolved;
    private AccountDTO account;
}
