package agus.ramdan.cdt.core.master.controller.dto.vendorcrew;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Vendor Crew")
public class VendorCrewCreateDTO {
    @Schema(description = "Nama Crew")
    private String name;

    @Schema(description = "KTP Crew")
    private String ktp;

    @Schema(description = "NPWP Crew")
    private String npwp;

    @Schema(description = "ID Vendor")
    @JsonProperty("vendor_id")
    private String vendorId;

    @Schema(description = "ID User")
    @JsonProperty("user_id")
    private String userId;

    @Schema(description = "Username Crew")
    private String username;

    @Schema(description = "Email Crew")
    private String email;

    @Schema(description = "MSIDN Crew")
    private String msisdn;
}
