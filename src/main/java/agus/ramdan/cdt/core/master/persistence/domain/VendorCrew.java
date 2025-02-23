package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
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
@Table(name = "cdt_vendor_crew")
@SQLDelete(sql = "UPDATE cdt_vendor_crew SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class VendorCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    @Embedded
    private AuditMetadata auditMetadata;

    @ManyToOne
    @JoinColumn(name= "vendor_id")
    private Vendor vendor;

    private String ktp;
    private String npwp;

    private UUID user_id;

    private String username;
    private String email;
    private String msidn;
}