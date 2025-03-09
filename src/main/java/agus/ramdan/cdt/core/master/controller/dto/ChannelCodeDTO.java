package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.HasId;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membaca Channel Code")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ChannelCodeDTO implements HasId {
    @Schema(description = "ID Channel Code dalam format String")
    private String id;

    @Schema(description = "Nama Channel Code")
    private String name;
}
