package project.dto;

import lombok.*;


/**
 * Created by Noumert on 13.08.2021.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserAccountDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Long id;
    private String accountNumber;
    private String accountName;
    private boolean ban;
    private String money;
}
