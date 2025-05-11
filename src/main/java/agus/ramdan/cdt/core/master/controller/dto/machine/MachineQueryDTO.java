package agus.ramdan.cdt.core.master.controller.dto.machine;

import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.controller.dto.ServiceLocationDTO;
import agus.ramdan.cdt.core.master.controller.dto.VendorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Machine")
public class MachineQueryDTO {
    private String id;
    private String code;
    private String name;
    private String description;
    @Schema(description = "Branch")
    private BranchDTO branch;
    @Schema(description = "Service Location")
    @JsonProperty("service_location")
    private ServiceLocationDTO serviceLocation;
    private VendorDTO supplier;
    private VendorDTO maintenance;
    private VendorDTO pjpur;
}
