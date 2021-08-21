package project.dto;

import lombok.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Noumert on 13.08.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopUpDTO {
    Long accountId;
    @NotNull @Min(value = 1)
    @Max(value = 99999)
    Double topUpMoney;
}
