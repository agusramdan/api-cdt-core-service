package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.cdt.core.master.persistence.embedded.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cdt_vendor", indexes = {
        @Index(name = "idx_vendor_email", columnList = "email"),
        @Index(name = "idx_vendor_msidn", columnList = "msidn"),
        @Index(name = "idx_vendor_npwp", columnList = "npwp")
})
@SQLDelete(sql = "UPDATE cdt_vendor SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    @Schema(description = "Name")
    private String name;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    @JsonProperty("created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    @JsonProperty("updated_on")
    private LocalDateTime updatedOn;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @JsonProperty("created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonProperty("updated_by")
    private String updatedBy;

    // Address
    @Embedded
    private Address address;  // Embedded Address

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

    @Column(name = "deleted", nullable = false)
    @JsonProperty("deleted")
    @Builder.Default
    private boolean deleted = false;
}
