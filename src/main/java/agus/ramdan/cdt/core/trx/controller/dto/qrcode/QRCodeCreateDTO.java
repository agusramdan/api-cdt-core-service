package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import agus.ramdan.cdt.core.master.controller.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO untuk membuat QR Code")
public class QRCodeCreateDTO {
    @Schema(description = "QR Code unik")
    private String code;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "Waktu kedaluwarsa QR Code")
    @JsonProperty("expired_time")
    private LocalDateTime expiredTime;

    @Schema(description = "Jenis QR Code")
    private String type;

    @Schema(description = "Informasi pengguna transaksi deprecated gunakan customer_crew")
    @JsonProperty("customer_crew")
    private CustomerCrewDTO customerCrew;
    private CustomerDTO customer;

    @Schema(description = "Informasi pengguna transaksi customer")
    @JsonProperty("customer_crew")
    private CustomerCrewDTO getCustomerCrew(){
        return user;
    }
    private void setCustomerCrew(CustomerCrewDTO customerCrew){
        this.user = customerCrew;
    }
    @Schema(description = "Informasi akun penerima manfaat")
    @JsonProperty("beneficiary_account")
    private BeneficiaryAccountDTO beneficiaryAccount;

    @Schema(description = "Informasi cabang manage QR Code")
    private BranchDTO branch;

    @JsonProperty("service_product")
    @Schema(description = "Informasi produk layanan terkait")
    private ServiceProductDTO serviceProduct;

    private CustomerCrewDTO user;
}

