package agus.ramdan.cdt.core.master.controller.dto.vendor;

import agus.ramdan.base.dto.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Vendor")
public class VendorQueryDTO {
    @Schema(description = "ID Vendor dalam format String")
    private String id;
    @Schema(description = "Nama Vendor")
    private String name;
    private String npwp;

    @Schema(description = "Email Vendor")
    private String email;
    private String phone;
    private Boolean supplier;
    private Boolean maintenance;
    private Boolean pjpur;
    private Boolean gateway;

    private AddressDTO address;
}
