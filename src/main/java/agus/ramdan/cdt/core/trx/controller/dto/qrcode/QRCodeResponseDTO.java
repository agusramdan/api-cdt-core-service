package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeResponseDTO {
    private UUID id;
    private LocalDateTime created_on;
    private LocalDateTime updated_on;
    private String created_by;
    private String updated_by;
    private String code;

    private LocalDateTime expired_time ;
    private boolean active=true;
    // multiple_trx_use
    // single_trx_use
    private String type;

    // customer/user information
    private UUID customer_id;
    private UUID customer_crew_id;
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