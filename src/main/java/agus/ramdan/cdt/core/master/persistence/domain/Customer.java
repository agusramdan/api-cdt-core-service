package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.Address;
import agus.ramdan.cdt.core.master.controller.dto.CustomerType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cdt_customer")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE cdt_customer SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedFilter_cdt_customer", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter_cdt_customer", condition = "deleted_at IS NULL") // Pengganti @Where
@Where(clause = "deleted_at IS NULL")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    @Schema(description = "Name")
    private String name;

    // Address
    @Embedded
    private Address address;  // Embedded Address

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    @JsonProperty("customer_type")
    @Builder.Default
    private CustomerType customerType = CustomerType.INDIVIDUAL;

    @Column(name = "ktp")
    @JsonProperty("ktp")
    private String ktp;

    @Column(name = "npwp")
    @JsonProperty("npwp")
    private String npwp;

    @Email
    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be in E.164 format")
    @Column(name = "msisdn")
    @JsonProperty("msisdn")
    private String msisdn;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Customer managed By branch")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("branch_id")
    private Branch branch;
}
