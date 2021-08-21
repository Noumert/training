package project.dto;

import lombok.*;


/**
 * Created by Noumert on 13.08.2021.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
