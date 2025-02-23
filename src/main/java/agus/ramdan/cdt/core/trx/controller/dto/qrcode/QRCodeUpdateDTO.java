package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeUpdateDTO {
    private UUID id;
    @Schema(description = "Status aktif QR Code")
    private String status;
    private boolean active;
}
