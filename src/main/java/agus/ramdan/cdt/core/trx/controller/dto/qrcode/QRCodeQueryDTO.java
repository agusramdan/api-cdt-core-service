package agus.ramdan.cdt.core.trx.controller.dto.qrcode;

import agus.ramdan.cdt.core.master.controller.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO untuk membaca data QR Code")
public class QRCodeQueryDTO {
    @Schema(description = "ID QR Code dalam format String")
    private String id;

    @Schema(description = "Kode QR unik")
    private String code;

    @Schema(description = "type")
    private String type;

    @Schema(description = "Status aktif QR Code")
    private String status;

    @Schema(description = "Status aktif QR Code")
    private Boolean active;

    @Schema(description = "Waktu kedaluwarsa QR Code")
    @JsonProperty("expired_time")
    private LocalDateTime expiredTime;

    private CustomerDTO customer;
    @Schema(description = "Informasi pengguna transaksi deprecated gunakan customer_crew")
    private CustomerCrewDTO user;

    @Schema(description = "Informasi pengguna transaksi customer")
    @JsonProperty("customer_crew")
    private CustomerCrewDTO getCustomerCrew(){
        return user;
    }
    private void setCustomerCrew(CustomerCrewDTO customerCrew){
        this.user = customerCrew;
    }
    @Schema(description = "Informasi cabang manage transaksi")
    private BranchDTO branch;

    @Schema(description = "Informasi akun penerima manfaat")
    @JsonProperty("beneficiary_account")
    private BeneficiaryAccountDTO beneficiaryAccount;

    @Schema(description = "Informasi produk layanan terkait")
    @JsonProperty("service_product")
    private ServiceProductDTO serviceProduct;

//    private VendorDTO vendor;
//    private VendorCrewDTO vendorCrew;
//
//    private MachineDTO machine;
}
