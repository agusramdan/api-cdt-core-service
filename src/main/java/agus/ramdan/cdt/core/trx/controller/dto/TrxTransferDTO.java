package agus.ramdan.cdt.core.trx.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxTransferDTO {
    private String id;
    @JsonProperty("trx_no")
    private String trxNo;
    private String status;
    private BigDecimal amount;
}

