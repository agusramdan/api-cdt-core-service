package agus.ramdan.cdt.core.trx.controller.dto.deposit;

import agus.ramdan.cdt.core.master.controller.dto.MachineDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositCreateDTO {
    @NotNull
    @NotEmpty
    private String token;
    private String username;

    @NotNull
    @NotEmpty
    private MachineDTO machine;

    @JsonProperty("machine_info")
    private String machineInfo;

    @JsonProperty("cdm_trx_no")
    @Schema(description = "Trx Number form cdm")
    @NotNull
    @NotEmpty
    private String cdmTrxNo;

    @NotNull
    @NotEmpty
    @JsonProperty("cdm_trx_date")
    private LocalDate cdmTrxDate;

    @JsonProperty("cdm_trx_time")
    private LocalTime cdmTrxTime;

    @JsonProperty("cdm_trx_type")
    private String cdmTrxType;

    @NotNull
    @NotEmpty
    private BigDecimal amount;

    private List<TrxDepositDenCreateDTO> denominations;
}
