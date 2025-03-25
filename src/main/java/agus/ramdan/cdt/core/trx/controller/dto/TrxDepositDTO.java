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
    private String token;
    private String status;
    @JsonProperty("service_transaction")
    private ServiceTransactionDTO serviceTransaction;
    @JsonProperty("service_product")
    private ServiceProductDTO serviceProduct;
    private MachineDTO machine;
    @JsonProperty("cdm_trx_no")
    private String cdmTrxNo;
    @JsonProperty("cdm_trx_date")
    private LocalDateTime cdmTrxDate;
    @JsonProperty("cdm_trx_no")
    private LocalDateTime cdmTrxTime;
    private BigDecimal amount;

}

