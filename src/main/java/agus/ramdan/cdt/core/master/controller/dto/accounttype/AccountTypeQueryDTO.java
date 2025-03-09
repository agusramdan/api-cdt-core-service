package agus.ramdan.cdt.core.master.controller.dto.accounttype;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "DTO untuk membaca Account Type")
public class AccountTypeQueryDTO {
    @Schema(description = "ID Account Type dalam format String")
    private String id;

    @Schema(description = "Nama Tipe Akun")
    private String name;

    @Schema(description = "Deskripsi Tipe Akun")
    private String description;
}
