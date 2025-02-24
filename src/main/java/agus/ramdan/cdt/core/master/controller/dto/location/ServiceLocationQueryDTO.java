package agus.ramdan.cdt.core.master.controller.dto.location;

import agus.ramdan.base.dto.AddressDTO;
import agus.ramdan.base.dto.CoordinateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Service Location")
public class ServiceLocationQueryDTO {
    @Schema(description = "ID Lokasi dalam format String")
    private String id;

    @Schema(description = "Kode Lokasi")
    private String code;

    @Schema(description = "Nama Lokasi")
    private String name;

    @Schema(description = "Alamat Lokasi")
    private AddressDTO address;

    @Schema(description = "Koordinat Lokasi")
    private CoordinateDTO coordinate;
}
