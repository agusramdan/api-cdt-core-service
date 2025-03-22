package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.domain.BaseEntity;
import agus.ramdan.base.embeddable.Address;
import agus.ramdan.base.embeddable.AuditMetadata;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "cdt_vendor", indexes = {
        @Index(name = "idx_vendor_email", columnList = "email"),
        @Index(name = "idx_vendor_npwp", columnList = "npwp")
})
@SQLDelete(sql = "UPDATE cdt_vendor SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vendor extends BaseEntity {

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

    @Column(name = "npwp")
    @JsonProperty("npwp")
    private String npwp;

    @Email
    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "phone")
    private String phone;

    // Type vendor service
    private Boolean supplier;
    private Boolean maintenance;
    private Boolean pjpur;
    private Boolean gateway;

}
