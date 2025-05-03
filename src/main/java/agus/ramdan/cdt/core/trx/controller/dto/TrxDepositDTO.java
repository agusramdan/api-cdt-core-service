package agus.ramdan.cdt.core.trx.controller.dto;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

