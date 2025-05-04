package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.CodeOrID;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Informasi produk layanan terkait")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ServiceProductDTO implements CodeOrID<String> {
    private String id;
    private String code;
    private String name;
}