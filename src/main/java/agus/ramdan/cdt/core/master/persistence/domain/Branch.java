package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.Address;
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
@Table(name = "cdt_branch")
@SQLDelete(sql = "UPDATE cdt_branch SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;

    private String name;
    @Column(unique = true)
    private String code;

    // Address
    @Embedded
    private Address address;  // Embedded Address

}