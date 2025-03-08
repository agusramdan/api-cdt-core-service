package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrxTransferRepository extends JpaRepository<TrxTransfer, UUID> {
}