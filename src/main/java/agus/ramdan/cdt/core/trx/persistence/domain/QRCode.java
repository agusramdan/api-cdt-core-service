package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.master.persistence.domain.*;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "qr_code", uniqueConstraints = {
        @UniqueConstraint(name = "uc_qrcode_code", columnNames = {"code"})
})
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class QRCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "code")
    @JsonIgnore
    @Schema(description = "QR Code")
    private String code;

    @Column(name = "expired_time")
    @JsonProperty("expired_time")
    private LocalDateTime expiredTime;

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type", nullable = false)
    @JsonProperty("code_type")
    private QRCodeType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty("status")
    private QRCodeStatus status;

    // customer/user information
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customer_crew_id")
    private CustomerCrew user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("beneficiary_account_id")
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("service_transaction_id")
    private ServiceTransaction serviceTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("service_product_id")
    private ServiceProduct serviceProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("vendor_crew_id")
    private VendorCrew vendorCrew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("machine_id")
    private Machine machine;

    @PrePersist
    protected void onCreate() {
        if (type == null) {
            type = QRCodeType.SINGLE_TRX_USE;
        }
        if (status == null) {
            status = QRCodeStatus.PENDING;
        }
        if (expiredTime == null) {
            if (type == QRCodeType.SINGLE_TRX_USE)
                expiredTime = LocalDateTime.now().plusDays(1);
            else
                expiredTime = LocalDateTime.now().plusYears(1);
        }
        if (status == QRCodeStatus.ACTIVE && expiredTime.isBefore(LocalDateTime.now())) {
            status = QRCodeStatus.EXPIRED;
        }
        active = status == QRCodeStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        if (expiredTime == null) {
            if (type == QRCodeType.SINGLE_TRX_USE)
                expiredTime = LocalDateTime.now().plusDays(1);
            else
                expiredTime = LocalDateTime.now().plusYears(1);
        }
        if (status == QRCodeStatus.ACTIVE && expiredTime.isBefore(LocalDateTime.now())) {
            status = QRCodeStatus.EXPIRED;
        }
        active = status == QRCodeStatus.ACTIVE;
    }
}