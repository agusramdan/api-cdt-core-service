package agus.ramdan.cdt.core.trx.controller.dto.transaction;

import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.trx.controller.dto.TrxDepositDTO;
import agus.ramdan.cdt.core.trx.controller.dto.TrxDepositPjpurDTO;
import agus.ramdan.cdt.core.trx.controller.dto.TrxTransferDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO untuk membaca transaction status")
public class ServiceTransactionQueryDTO {
    @Schema(description = "ID ")
    private String id;
    private String no;
    @JsonProperty("trx_date")
    private LocalDateTime trxDate;
    @Schema(description = "Status transaksi")
    private String status;
    @Schema(description = "Informasi produk layanan terkait")
    @JsonProperty("service_product")
    private ServiceProductDTO serviceProduct;
    private TrxDepositDTO deposit;
    @JsonProperty("deposit_pjpur")
    private TrxDepositPjpurDTO depositPjpur;
    private TrxTransferDTO transfer;
}
