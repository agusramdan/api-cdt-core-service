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

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "t_account")
@SQLDelete(sql = "UPDATE t_account SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class Wallet {

    @Id
    @Column(name = "number")
    @JsonProperty(index = 1)
    @Schema(description = "Wallet Number")
    private String number;
    @Schema(description = "Name")
    private String name;

    private BigDecimal balance;

    @Embedded
    private AuditMetadata auditMetadata;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}