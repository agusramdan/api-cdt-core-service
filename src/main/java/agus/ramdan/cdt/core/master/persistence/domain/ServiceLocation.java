package agus.ramdan.cdt.core.master.persistence.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "cdt_service_location")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class ServiceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

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