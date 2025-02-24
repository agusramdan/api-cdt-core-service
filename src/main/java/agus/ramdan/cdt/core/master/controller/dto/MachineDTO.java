package agus.ramdan.cdt.core.master.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MachineDTO", description = "DTO untuk mesin")
public class MachineDTO {
    private String id;
    private String code;
    private String name;
    private String description;

    @Schema(description = "Branch ID")
    @JsonProperty("branch_id")
    private String branchId;

    @Schema(description = "Service Location ID")
    @JsonProperty("service_location_id")
    private String serviceLocationId;
}
