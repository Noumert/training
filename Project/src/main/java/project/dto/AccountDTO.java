package project.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Noumert on 13.08.2021.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private String accountName;
    @NotNull
    @NotEmpty
    private String money;
    @NotNull
    @NotEmpty
    private String accountNumber;
    private boolean ban;
}
