package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceTransactionRepository extends JpaRepository<ServiceTransaction, UUID>, JpaSpecificationExecutor<ServiceTransaction> {
    Optional<ServiceTransaction> findByNo(String no);

    List<ServiceTransaction> findByStatusNot(TrxStatus status, Pageable pageable);
    List<ServiceTransaction> findByStatus(TrxStatus status, Pageable pageable);
}