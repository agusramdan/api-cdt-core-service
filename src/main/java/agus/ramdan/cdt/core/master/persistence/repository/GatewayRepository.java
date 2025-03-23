package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface GatewayRepository extends JpaRepository<Gateway, UUID>, JpaSpecificationExecutor<Gateway> {
    Optional<Gateway> findByCode(String code);
}