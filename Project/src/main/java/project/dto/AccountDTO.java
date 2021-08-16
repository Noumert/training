package project.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
