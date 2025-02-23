package agus.ramdan.cdt.core.master.controller.dto.customer;

import agus.ramdan.base.dto.AddressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "CustomerQueryDTO", description = "DTO for querying customer details")
public class CustomerQueryDTO {

    @Schema(description = "Unique Customer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Customer Full Name", example = "John Doe")
    private String name;

    @JsonProperty("customer_type")
    private String customer_type;

    @JsonProperty("ktp")
    private String ktp;

    @JsonProperty("npwp")
    private String npwp;

    @Email
    @Schema(description = "Customer Email Address", example = "john.doe@example.com")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be in E.164 format")
    @Schema(description = "Customer Mobile Number", example = "+6281234567890")
    private String msidn;

    @Schema(description = "Customer Address Details")
    private AddressDTO address;
}

