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
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrxDepositCreateDTO {
    @NotNull
    @NotEmpty
    private String token;
    @NotNull
    @NotEmpty
    private String signature;
    @NotNull
    @NotEmpty
    private MachineDTO machine;
    @JsonProperty("cdm_trx_no")
    @Schema(description = "Trx Number form cdm")
    @NotNull
    @NotEmpty
    private String cdm_trx_no;
    private LocalDateTime cdm_trx_date;
    private LocalDateTime cdm_trx_time;
    @NotNull
    @NotEmpty
    private BigDecimal amount;
    private List<TrxDepositDenCreateDTO> denominations;
}
