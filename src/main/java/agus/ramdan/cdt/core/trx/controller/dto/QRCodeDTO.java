package agus.ramdan.cdt.core.trx.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "DTO untuk membaca data QR Code")
@NoArgsConstructor
public class QRCodeDTO {
    public QRCodeDTO(String code) {
        this.code = code;
    }

    @Schema(description = "ID QR Code dalam format String")
    private String id;

    @Schema(description = "Kode QR unik")
    private String code;

    @Schema(description = "Status aktif QR Code")
    private Boolean active;

    @Schema(description = "Waktu kedaluwarsa QR Code")
    private String expired_time;
}
