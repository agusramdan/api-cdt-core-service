package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositDenominationResponseDTO {
    private UUID id;
    private BigDecimal denomination;
    private Integer quantity;
    private BigDecimal amount;
    private String type;
}
