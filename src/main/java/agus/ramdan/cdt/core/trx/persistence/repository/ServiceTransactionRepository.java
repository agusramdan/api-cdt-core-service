package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceTransactionRepository extends JpaRepository<ServiceTransaction, UUID>, JpaSpecificationExecutor<ServiceTransaction> {
    Optional<ServiceTransaction> findByNo(String no);
    @Query("SELECT st FROM ServiceTransaction st WHERE st.status !=  'SUCCESS'")
    List<ServiceTransaction> findAllByNotSuccess(Pageable pageable);
}