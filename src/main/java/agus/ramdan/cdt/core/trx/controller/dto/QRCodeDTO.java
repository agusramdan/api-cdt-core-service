package agus.ramdan.cdt.core.trx.controller.dto;

import agus.ramdan.base.dto.CodeOrID;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO untuk membaca data QR Code")
@NoArgsConstructor
public class QRCodeDTO implements CodeOrID<String> {
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
    @JsonProperty("expired_time")
    private LocalDateTime expiredTime;
}
