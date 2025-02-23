package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.Address;
import agus.ramdan.base.embeddable.AuditMetadata;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "cdt_customer", indexes = {
        @Index(name = "idx_customer_email", columnList = "email"),
        @Index(name = "idx_customer_msidn", columnList = "msidn"),
        @Index(name = "idx_customer_npwp", columnList = "npwp")
})
@SQLDelete(sql = "UPDATE cdt_customer SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;

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

    @Column(name = "ktp", unique = true)
    @JsonProperty("ktp")
    private String ktp;

    @Column(name = "npwp", unique = true)
    @JsonProperty("npwp")
    private String npwp;

    @Email
    @Column(name = "email", unique = true)
    @JsonProperty("email")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be in E.164 format")
    @Column(name = "msidn", unique = true)
    @JsonProperty("msidn")
    private String msidn;

}
