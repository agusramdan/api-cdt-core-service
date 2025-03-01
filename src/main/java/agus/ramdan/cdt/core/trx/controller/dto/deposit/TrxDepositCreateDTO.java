package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositCreateDTO {
    private String token;
    private String signature;

//    @JsonProperty("service_transaction")
//    private ServiceTransactionDTO serviceTransaction;
//
//    @JsonProperty("service_product")
//    private ServiceProduct serviceProduct;

    private MachineDTO machine;

    @JsonProperty("cdm_trx_no")
    @Schema(description = "Trx Number form cdm")
    private String cdm_trx_no;

    private LocalDateTime cdm_trx_date;
    private LocalDateTime cdm_trx_time;

    private BigDecimal amount;
    private List<TrxDepositDenCreateDTO> denominations;
}
