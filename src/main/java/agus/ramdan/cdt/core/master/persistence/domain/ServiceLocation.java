package agus.ramdan.cdt.core.master.persistence.domain;

import agus.ramdan.base.embeddable.AuditMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "cdt_service_location")
@SQLDelete(sql = "UPDATE cdt_service_location SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class ServiceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Embedded
    private AuditMetadata auditMetadata;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    // Address Start
    @Column
    private String street1;
    @Column
    private String street2;
    @Column
    private String city;
    @Column
    private String zip_code;
    @Column
    private String country;
    // Address End

    @Column
    private Float longitude ;
    @Column
    private Float latitude;
}