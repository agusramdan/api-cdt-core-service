package agus.ramdan.cdt.core.master.persistence.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "t_account")
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_on;
    @UpdateTimestamp
    private LocalDateTime updated_on;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    public String created_by;

    @LastModifiedBy
    @Column(name = "updated_by")
    public String updated_by;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}