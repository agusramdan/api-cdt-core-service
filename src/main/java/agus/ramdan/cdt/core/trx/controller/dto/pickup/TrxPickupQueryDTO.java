package agus.ramdan.cdt.core.trx.controller.dto.pickup;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membaca transaksi Pickup")
public class TrxPickupQueryDTO {
    @Schema(description = "ID Transaksi Pickup dalam format String")
    private String id;
    private String username;
    private MachineDTO machine;
    @JsonProperty("machine_info")
    private String machineInfo;
    @JsonProperty("cdm_trx_no")
    @Schema(description = "Trx Number form cdm")
    private String cdmTrxNo;
    @Schema(description = "Pickup Date")
    @JsonProperty("cdm_trx_date")
    private LocalDate cdmTrxDate;
    @Schema(description = "Time of pickup")
    @JsonProperty("cdm_trx_time")
    private LocalTime cdmTrxTime;
    @JsonProperty("cdm_trx_type")
    private String cdmTrxType;
    @JsonProperty("total_pieces")
    private Integer totalPieces;
    @JsonProperty("new_banknote_bag_no")
    private String newBanknoteBagNo;
    @JsonProperty("old_banknote_bag_no")
    private String oldBanknoteBagNo;
    @Schema(description = "Total Amount")
    private BigDecimal amount;
    @JsonProperty("otp_used")
    private String otpUsed;
    @JsonProperty("reset_bag_time")
    private LocalDateTime resetBagTime;
    @JsonProperty("last_action")
    private String lastAction;
    @JsonProperty("action_date")
    private LocalDate actionDate;
    @JsonProperty("action_start_time")
    private LocalTime actionStartTime;
    @JsonProperty("action_end_time")
    private LocalTime actionEndTime;

    @Schema(description = "Denominasi Pickup")
    private List<TrxPickupDenomCreateDTO> denominations;
}
