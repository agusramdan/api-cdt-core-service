package agus.ramdan.cdt.core.trx.controller.dto.deposit;

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
    private List<TrxDepositDenCreateDTO> denominations;
}
