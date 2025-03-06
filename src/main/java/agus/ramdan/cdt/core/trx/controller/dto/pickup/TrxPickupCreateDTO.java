package agus.ramdan.cdt.core.trx.controller.dto.pickup;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membuat transaksi Pickup")
public class TrxPickupCreateDTO {
    private String signature;

    @Schema(description = "ID Mesin")
    private String machineId;

    @Schema(description = "Informasi Mesin")
    private String machineInfo;

    @Schema(description = "Status Transaksi Pickup")
    private String status;

    @Schema(description = "Nomor Referensi CDM")
    private String cdmTrxNo;

    @Schema(description = "Tanggal Transaksi CDM")
    private LocalDateTime cdmTrxDate;

    @Schema(description = "Tanggal Transaksi")
    private LocalDateTime trxDate;

    @Schema(description = "Total Amount")
    private BigDecimal amount;

    @Schema(description = "Denominasi Pickup")
    private List<TrxPickupDenomCreateDTO> denominations;
}
