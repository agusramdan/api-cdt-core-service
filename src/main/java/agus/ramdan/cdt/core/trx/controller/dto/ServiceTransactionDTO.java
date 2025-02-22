package agus.ramdan.cdt.core.trx.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Informasi transaksi terkait")
public class ServiceTransactionDTO {
    private String id;
    private String no;
}
