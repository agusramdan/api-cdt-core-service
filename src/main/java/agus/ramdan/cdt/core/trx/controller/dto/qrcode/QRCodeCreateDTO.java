package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.controller.dto.CustomerCrewDTO;
import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceTransactionDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk membuat QR Code")
public class QRCodeCreateDTO {
    @Schema(description = "QR Code unik")
    private String code;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "Waktu kedaluwarsa QR Code")
    private String expired_time;

    @Schema(description = "Jenis QR Code")
    private String type;

    @Schema(description = "Informasi pengguna transaksi")
    private CustomerCrewDTO user;

    @Schema(description = "Informasi akun penerima manfaat")
    @JsonProperty("beneficiary_account")
    private BeneficiaryAccountDTO beneficiaryAccount;

    @Schema(description = "Informasi cabang manage QR Code")
    private BranchDTO branch;

    @Schema(description = "Informasi transaksi terkait")
    @JsonProperty("service_transaction")
    private ServiceTransactionDTO serviceTransaction;

    @JsonProperty("service_product")
    @Schema(description = "Informasi produk layanan terkait")
    private ServiceProductDTO serviceProduct;
}

