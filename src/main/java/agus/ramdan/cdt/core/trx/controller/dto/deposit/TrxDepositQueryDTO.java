package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceTransactionDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositQueryDTO {
    private UUID id;
    private String token;
    private String status;

    @JsonProperty("service_transaction")
    private ServiceTransactionDTO serviceTransaction;

    @JsonProperty("service_product")
    private ServiceProductDTO serviceProduct;

    private MachineDTO machine;
    @JsonProperty("machine_info")
    private String machineInfo;
    @JsonProperty("cdm_trx_no")
    private String cdmTrxNo;
    @JsonProperty("cdm_trx_date")
    private LocalDate cdmTrxDate;
    @JsonProperty("cdm_trx_time")
    private LocalTime cdmTrxTime;
    @JsonProperty("cdm_trx_type")
    private String cdmTrxType;

    private BigDecimal amount;

    private List<TrxDepositDenQueryDTO> denominations;
}
