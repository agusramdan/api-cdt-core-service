package agus.ramdan.cdt.core.master.controller.dto.machine;

import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.controller.dto.ServiceLocationDTO;
import agus.ramdan.cdt.core.master.controller.dto.VendorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Machine")
public class MachineCreateDTO {

    @Schema(description = "Kode Mesin")
    private String code;

    @Schema(description = "Nama Mesin")
    private String name;
    @Schema(description = "Description")
    private String description;
    @Schema(description = "Branch ID")
    @JsonProperty("branch_id")
    private String branchId;

//    @Schema(description = "Service Location ID")
//    @JsonProperty("service_location_id")
//    private String serviceLocationId;

    @Schema(description = "Branch")
    private BranchDTO branch;
    @Schema(description = "Service Location")
    @JsonProperty("service_location")
    private ServiceLocationDTO serviceLocation;
    private VendorDTO supplier;
    private VendorDTO maintenance;
    private VendorDTO pjpur;
}
