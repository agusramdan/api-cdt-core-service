package agus.ramdan.cdt.core.trx.controller.dto.pickup;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxPickupDenomCreateDTO {
    @NotNull
    private BigDecimal denomination;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String type;
}

