package agus.ramdan.cdt.core.master.controller.dto.vendor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Vendor")
public class VendorCreateDTO {
    @Schema(description = "Kode Vendor")
    private String code;

    @Schema(description = "Nama Vendor")
    private String name;

    @Schema(description = "Email Vendor")
    private String email;

    @Schema(description = "Nomor Telepon Vendor")
    private String phone;

    @Schema(description = "Tipe Vendor (Supplier, Maintenance, PJPur, Gateway)")
    private String type;
}

