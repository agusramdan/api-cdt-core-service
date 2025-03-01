package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceTransactionDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositQueryDTO {
    private UUID id;
    private String token;
    private String status;

//    private LocalDateTime createdOn;
//    private LocalDateTime updatedOn;

    @JsonProperty("service_transaction")
    private ServiceTransactionDTO serviceTransaction;

    @JsonProperty("service_product")
    private ServiceProduct serviceProduct;

    private MachineDTO machine;

    private String cdm_trx_no;

    private LocalDateTime cdm_trx_date;
    private LocalDateTime cdm_trx_time;

    private BigDecimal amount;

    private List<TrxDepositDenQueryDTO> denominations;
}

