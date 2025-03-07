package agus.ramdan.cdt.core.master.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Vendor Crew")
public class VendorCrewDTO {
    @Schema(description = "ID Crew dalam format String")
    private String id;

    @Schema(description = "Nama Crew")
    private String name;

    @Schema(description = "Username Crew")
    private String username;

    @Schema(description = "Email Crew")
    private String email;

    @Schema(description = "MSIDN Crew")
    private String msisdn;
}
