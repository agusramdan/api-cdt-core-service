package agus.ramdan.cdt.core.trx.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Informasi pengguna transaksi")
public class TrxUserDTO {
    private String customer_id;
    private String customer_crew_id;
    private String username;
}