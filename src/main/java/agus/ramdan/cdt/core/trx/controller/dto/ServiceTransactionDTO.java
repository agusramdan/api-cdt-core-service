package agus.ramdan.cdt.core.trx.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "InformasiTransaksi")
public class ServiceTransactionDTO {
    private String id;
    private String no;
    private LocalDateTime trxDate;
}
