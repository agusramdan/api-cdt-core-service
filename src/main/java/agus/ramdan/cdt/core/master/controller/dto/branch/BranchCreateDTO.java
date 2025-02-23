package agus.ramdan.cdt.core.master.controller.dto.branch;

import agus.ramdan.base.dto.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat Branch")
public class BranchCreateDTO {
    @Schema(description = "Nama Cabang")
    private String name;

    @Schema(description = "Kode Cabang")
    private String code;

    @Schema(description = "Alamat Cabang")
    private AddressDTO address;
}
