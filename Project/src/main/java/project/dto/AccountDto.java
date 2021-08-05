package project.dto;

import com.sun.istack.NotNull;
import lombok.*;
import project.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDto {
    @NotNull
    @NotEmpty
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
    @NotNull
    @NotEmpty
    private boolean ban;
}
