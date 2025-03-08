package agus.ramdan.cdt.core.master.controller.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AccountType", description = "Account Type")
public class AccountTypeDTO {

    @Schema(description = "System", example = "SVN")
    private String id;

    @Schema(description = "Name", example = "Saving")
    private String name;

}
