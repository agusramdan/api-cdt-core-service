package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeUpdateDTO {
    private UUID id;
    private boolean active;
}
