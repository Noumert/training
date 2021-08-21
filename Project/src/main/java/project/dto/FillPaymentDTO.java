package project.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Noumert on 13.08.2021.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FillPaymentDTO {
    private Long id;
    @NonNull
    private Long accountId;
    @NotNull @Min(value = 1)
    @Max(value = 99999)
    private String paymentMoney;
    @NotEmpty
    @NotNull
    private String recipient;
}
