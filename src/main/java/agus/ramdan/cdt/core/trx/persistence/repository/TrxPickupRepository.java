package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TrxPickupRepository extends JpaRepository<TrxPickup, UUID>, JpaSpecificationExecutor<TrxPickup> {
}