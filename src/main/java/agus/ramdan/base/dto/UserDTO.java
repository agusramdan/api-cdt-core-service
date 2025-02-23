package agus.ramdan.base.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Branch", description = "Branch")
public class UserDTO {
    private String id;
    private String username;
    private String name;
    private String email;
    private String msisdn;
}
