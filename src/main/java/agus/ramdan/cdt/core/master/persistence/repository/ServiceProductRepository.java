package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ServiceProductRepository extends JpaRepository<ServiceProduct, UUID>, JpaSpecificationExecutor<ServiceProduct> {
    Optional<ServiceProduct> findByCode(String code);
}