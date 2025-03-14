package agus.ramdan.cdt.core.trx.controller.dto.pickup;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membuat transaksi Pickup")
public class TrxPickupCreateDTO {

    @NotNull
    private MachineDTO machine;
    @JsonProperty("machine_info")
    private String machineInfo;
    private String username;

    @JsonProperty("cdm_trx_no")
    @Schema(description = "Trx Number form cdm")
    @NotNull
    @NotEmpty
    private String cdmTrxNo;

    @NotNull
    @Schema(description = "Pickup Date")
    @JsonProperty("cdm_trx_date")
    private LocalDate cdmTrxDate;

    @Schema(description = "Time of pickup")
    @JsonProperty("cdm_trx_time")
    private LocalTime cdmTrxTime;

    @JsonProperty("cdm_trx_type")
    private String cdmTrxType;

    @NotNull
    @JsonProperty("total_pieces")
    private Integer totalPieces;

    @JsonProperty("new_banknote_bag_no")
    private String newBanknoteBagNo;

    @JsonProperty("old_banknote_bag_no")
    private String oldBanknoteBagNo;

    @Schema(description = "Total Amount")
    @NotNull
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
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime actionStartTime;

    @JsonProperty("action_end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime actionEndTime;

    @Schema(description = "Denominasi Pickup")
    @Valid
    private List<TrxPickupDenomCreateDTO> denominations;
}
