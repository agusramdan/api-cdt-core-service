package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TrxDepositPjpurRepository extends JpaRepository<TrxDepositPjpur, UUID>, JpaSpecificationExecutor<TrxDepositPjpur> {
}