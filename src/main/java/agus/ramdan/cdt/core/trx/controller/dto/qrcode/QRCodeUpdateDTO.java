package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import agus.ramdan.cdt.core.master.controller.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeUpdateDTO {
    @Schema(description = "Status aktif QR Code")
    private String status;

    @Schema(description = "Informasi akun penerima manfaat")
    @JsonProperty("beneficiary_account")
    private BeneficiaryAccountDTO beneficiaryAccount;

    @Schema(description = "Informasi pengguna transaksi")

    @JsonProperty("customer_crew")
    private CustomerCrewDTO customerCrew;
    private CustomerDTO customer;

    @Schema(description = "Informasi cabang manage QR Code")
    private BranchDTO branch;

    @Schema(description = "Waktu kedaluwarsa QR Code")
    @JsonProperty("expired_time")
    private LocalDateTime expiredTime;

//    @Schema(description = "Informasi transaksi terkait")
//    @JsonProperty("service_transaction")
//    private ServiceTransactionDTO serviceTransaction;

    @JsonProperty("service_product")
    @Schema(description = "Informasi produk layanan terkait")
    private ServiceProductDTO serviceProduct;


    private CustomerCrewDTO user;
}
