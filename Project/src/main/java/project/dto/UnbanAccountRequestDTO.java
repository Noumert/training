package project.dto;

import lombok.*;

/**
 * Created by Noumert on 13.08.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnbanAccountRequestDTO {
    private Long id;
    private String dateTime;
    private boolean resolved;
    private AccountDTO account;
}
