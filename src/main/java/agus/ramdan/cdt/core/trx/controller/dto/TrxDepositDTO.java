package agus.ramdan.cdt.core.trx.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositDTO {
    private UUID id;
    private String token;
    private String status;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private String service_transaction_id;
    private String service_transaction_no;

    private String service_product_id;
    private String service_product_code;
    private String service_product_name;


    private String machine_deposit_id;
    private String machine_deposit_code;
    private String machine_deposit_sn;

    private String cdm_trx_no;
    private LocalDateTime cdm_trx_date;
    private LocalDateTime cdm_trx_time;

    private BigDecimal amount;

}

