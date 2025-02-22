package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQRCodeCommandDTO {

    private String code;

    private LocalDateTime expired_time ;
    private boolean active=true;
    // multiple_trx_use
    // single_trx_use
    private String type;

    // customer/user information
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid UUID format for customer_id")
    @Schema(description = "Customer ID associated with this account", example = "550e8400-e29b-41d4-a716-446655440000")
    private String customer_id;

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid UUID format for customer_id")
    @Schema(description = "Customer ID associated with this account", example = "550e8400-e29b-41d4-a716-446655440000")
    private String customer_crew_id;
    private String username;

    // account information
    private String account_number;
    private String account_name;
    private String bank_code;
    private String bank_name;

    private String service_transaction_id;
    private String service_transaction_no;

    private String service_product_id;
    private String service_product_code;
    private String service_product_name;

}
