package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.CodeOrID;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MachineDTO", description = "DTO untuk mesin")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MachineDTO implements CodeOrID<String> {
    private String id;
    private String code;
    private String name;
//    private String description;
//
//    @Schema(description = "Branch ID")
//    @JsonProperty("branch_id")
//    private String branchId;
//
//    @Schema(description = "Service Location ID")
//    @JsonProperty("service_location_id")
//    private String serviceLocationId;
}
