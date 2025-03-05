package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cdt_beneficiary_account")
@SQLDelete(sql = "UPDATE cdt_beneficiary_account SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class BeneficiaryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // account information
    private String account_number;
    private String account_name;

    @ManyToOne
    @JoinColumn(name = "account_type")
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "customer_type")
    private CustomerType customerType;

    @ManyToOne
    @JoinColumn(name = "customer_status")
    private CustomerType customerStatus;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    // static qe
    @ManyToOne
    @JoinColumn(name = "cr_code_id")
    private QRCode qrCode;

}