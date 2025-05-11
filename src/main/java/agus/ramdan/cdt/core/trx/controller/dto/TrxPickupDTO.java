package agus.ramdan.cdt.core.trx.controller.dto;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membaca transaksi Pickup")
public class TrxPickupDTO {
    @Schema(description = "ID Transaksi Pickup dalam format String")
    private String id;

    @Schema(description = "Mesin")
    private MachineDTO machine;

    @Schema(description = "Status Transaksi Pickup")
    private String status;
    @JsonProperty("cdm_trx_no")
    @Schema(description = "Nomor Referensi CDM")
    private String cdmTrxNo;

    @Schema(description = "Tanggal Transaksi CDM")
    @JsonProperty("cdm_trx_date")
    private LocalDateTime cdmTrxDate;

    @Schema(description = "Tanggal Transaksi")
    @JsonProperty("trx_date")
    private LocalDateTime trxDate;

    @Schema(description = "Total Amount")
    private BigDecimal amount;

}
