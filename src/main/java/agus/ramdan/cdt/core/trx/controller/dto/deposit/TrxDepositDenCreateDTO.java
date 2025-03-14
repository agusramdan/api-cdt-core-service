package agus.ramdan.cdt.core.trx.controller.dto.deposit;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositDenCreateDTO {
    @NotNull
    private BigDecimal denomination;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal amount;
    private String type;
}

