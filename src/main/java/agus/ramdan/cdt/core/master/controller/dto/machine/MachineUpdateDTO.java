package agus.ramdan.cdt.core.master.controller.dto.machine;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk memperbarui Machine")
public class MachineUpdateDTO {

    @Schema(description = "Kode Mesin")
    private String code;

    @Schema(description = "Nama Mesin")
    private String name;

    private String description;

    @Schema(description = "Branch ID")
    @JsonProperty("branch_id")
    private String branchId;

    @Schema(description = "Service Location ID")
    @JsonProperty("service_location_id")
    private String serviceLocationId;
}
