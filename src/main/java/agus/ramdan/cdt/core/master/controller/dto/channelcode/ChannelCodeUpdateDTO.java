package agus.ramdan.cdt.core.master.controller.dto.channelcode;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk memperbarui Channel Code")
public class ChannelCodeUpdateDTO {
    @Schema(description = "ID Channel Code dalam format String")
    private String id;

    @Schema(description = "Nama Channel Code")
    private String name;

    @Schema(description = "Deskripsi Channel Code")
    private String description;
}
