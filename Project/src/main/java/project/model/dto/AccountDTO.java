package project.model.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDTO {
    @NotNull
    @NotEmpty
    private Long id;
    @NotNull
    @NotEmpty
    private String accountName;
    @NotNull
    @NotEmpty
    private Long money;
    @NotNull
    @NotEmpty
    private String accountNumber;
    @NotNull
    @NotEmpty
    private boolean ban;
}
