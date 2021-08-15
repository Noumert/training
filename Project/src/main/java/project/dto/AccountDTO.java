package project.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDTO {
    private Long id;
    private String accountName;
    private String money;
    private String accountNumber;
    private boolean ban;
}
