package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Embedded
    private AuditMetadata auditMetadata;

    @Column(name = "code")
    @JsonProperty(index = 2)
    @Schema(description = "QR Code")
    private String code;

    private LocalDateTime expired_time ;
    private boolean active=true;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type", nullable = false)
    @JsonProperty("code_type")
    private QRCodeType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty("status")
    private QRCodeStatus status;

    // customer/user information
    @ManyToOne
    private CustomerCrew user;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private BeneficiaryAccount beneficiaryAccount;

    @ManyToOne
    private ServiceTransaction serviceTransaction;

    @ManyToOne
    private ServiceProduct serviceProduct;

    @PrePersist
    protected void onCreate() {
        if (type == null) {
            type = QRCodeType.SINGEL_TRX_USE;
        }
        if (status == null) {
            status = QRCodeStatus.PENDING;
        }
    }

}