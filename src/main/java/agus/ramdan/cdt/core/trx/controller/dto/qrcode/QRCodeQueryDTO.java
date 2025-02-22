package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import agus.ramdan.cdt.core.trx.controller.dto.BeneficiaryAccountDTO;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceTransactionDTO;
import agus.ramdan.cdt.core.trx.controller.dto.TrxUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membaca data QR Code")
public class QRCodeQueryDTO {
    @Schema(description = "ID QR Code dalam format String")
    private String id;

    @Schema(description = "Kode QR unik")
    private String code;

    @Schema(description = "Status aktif QR Code")
    private Boolean active;

    @Schema(description = "Waktu kedaluwarsa QR Code")
    private String expired_time;

    @Schema(description = "Informasi pengguna transaksi")
    private TrxUserDTO user;

    @Schema(description = "Informasi akun penerima manfaat")
    private BeneficiaryAccountDTO beneficiaryAccount;

    @Schema(description = "Informasi transaksi terkait")
    private ServiceTransactionDTO serviceTransaction;

    @Schema(description = "Informasi produk layanan terkait")
    private ServiceProductDTO serviceProduct;
}
