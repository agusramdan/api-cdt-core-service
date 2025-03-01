package agus.ramdan.cdt.core.master.controller.dto.customercrew;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data Customer Crew")
public class CustomerCrewQueryDTO {
    @Schema(description = "ID Crew dalam format String")
    private String id;

    @Schema(description = "Nama Crew")
    private String name;

    @Schema(description = "KTP Crew")
    private String ktp;

    @Schema(description = "NPWP Crew")
    private String npwp;

    @Schema(description = "ID Customer")
    @JsonProperty("customer_id")
    private String customerId;


    @Schema(description = "Username Crew")
    private String username;

    @Schema(description = "Email Crew")
    private String email;

    @Schema(description = "MSIDN Crew")
    private String msidn;
}
