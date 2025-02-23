package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositDenResponseDTO {
    private String id;
    private BigDecimal denomination;
    private Integer quantity;
    private BigDecimal amount;
    private String type;
}
