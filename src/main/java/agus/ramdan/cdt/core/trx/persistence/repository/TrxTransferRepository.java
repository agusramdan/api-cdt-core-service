package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrxTransferRepository extends JpaRepository<TrxTransfer, UUID> {
    @Query("SELECT t FROM TrxTransfer t WHERE t.transaction.no = ?1 limit 1")
    List<TrxTransfer> findByTransactionNo(String transactionNo);
    List<TrxTransfer> findByTrxNo(String transactionNo);
}