package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.TID;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Vendor")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VendorDTO implements TID<String> {
    @Schema(description = "ID Vendor dalam format String")
    private String id;

    @Schema(description = "Nama Vendor")
    private String name;

}
