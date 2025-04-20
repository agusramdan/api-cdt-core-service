package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ServiceTransactionRepository extends JpaRepository<ServiceTransaction, UUID>, JpaSpecificationExecutor<ServiceTransaction> {
    Optional<ServiceTransaction> findByNo(String no);
}