package agus.ramdan.cdt.core.master.controller.dto;

import agus.ramdan.base.dto.AddressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(name = "CustomerQueryDTO", description = "DTO for querying customer details")
public class CustomerDTO {

    @Schema(description = "Unique Customer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Customer Full Name", example = "John Doe")
    private String name;
}

