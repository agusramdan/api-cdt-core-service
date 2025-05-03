package agus.ramdan.cdt.core.trx.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositDTO {
    private String id;
    private String status;
    @JsonProperty("cdm_trx_no")
    private String cdmTrxNo;
    private BigDecimal amount;

}

