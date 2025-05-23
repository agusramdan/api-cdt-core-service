package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
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
//@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BeneficiaryAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String firstname;
    private String lastname;

    // account information
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_name")
    private String accountName;

    @ManyToOne
    @JoinColumn(name = "account_type")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("account_type")
    @NotFound(action = NotFoundAction.IGNORE)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @JsonIdentityReference(alwaysAsId = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "customer_type")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customer_type")
    @NotFound(action = NotFoundAction.IGNORE)
    private CustomerType customerType;

    @ManyToOne
    @JoinColumn(name = "customer_status")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customer_status")
    @NotFound(action = NotFoundAction.IGNORE)
    private CustomerStatus customerStatus;

    @ManyToOne
    @JoinColumn(name = "region_code")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("region_code")
    @NotFound(action = NotFoundAction.IGNORE)
    private RegionCode regionCode;

    @ManyToOne
    @JoinColumn(name = "country_code")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("country_code")
    @NotFound(action = NotFoundAction.IGNORE)
    private CountryCode countryCode;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("branch_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Branch branch;

    // static qe
    @ManyToOne
    @JoinColumn(name = "cr_code_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("qr_code_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private QRCode qrCode;

}