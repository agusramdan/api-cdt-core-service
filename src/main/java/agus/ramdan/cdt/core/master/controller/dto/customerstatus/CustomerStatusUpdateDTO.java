package agus.ramdan.cdt.core.master.controller.dto.customerstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk memperbarui Customer Status")
public class CustomerStatusUpdateDTO {
    @Schema(description = "ID Customer Status dalam format String")
    private String id;

    @Schema(description = "Nama Customer Status")
    private String name;

    @Schema(description = "Deskripsi Customer Status")
    private String description;
}

