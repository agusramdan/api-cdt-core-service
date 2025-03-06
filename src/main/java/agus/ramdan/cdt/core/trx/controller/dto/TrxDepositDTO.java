package agus.ramdan.cdt.core.trx.controller.dto;

import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
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

    private ServiceTransactionDTO serviceTransaction;
    private ServiceProductDTO serviceProduct;
    private Machine machine;

    private String cdm_trx_no;

    private LocalDateTime cdm_trx_date;
    private LocalDateTime cdm_trx_time;

    private BigDecimal amount;

}

