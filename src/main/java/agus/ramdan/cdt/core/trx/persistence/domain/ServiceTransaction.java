package agus.ramdan.cdt.core.trx.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "service_trx")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class ServiceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(index = 1)
    private UUID id;
    private String no;

    @Embedded
    private AuditMetadata auditMetadata;

    private TrxStatus status;
}