package agus.ramdan.cdt.core.master.controller.dto.customertype;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membaca Customer Type")
public class CustomerTypeQueryDTO {
    @Schema(description = "ID Customer Type dalam format String")
    private String id;

    @Schema(description = "Nama Customer Type")
    private String name;

    @Schema(description = "Deskripsi Customer Type")
    private String description;
}

