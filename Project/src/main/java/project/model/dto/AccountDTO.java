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
    private Long id;
    @NotNull
    @NotEmpty
    private String accountName;
    @NotNull
    private Long money;
    @NotNull
    @NotEmpty
    private String accountNumber;
    @NotNull
    private boolean ban;
}
