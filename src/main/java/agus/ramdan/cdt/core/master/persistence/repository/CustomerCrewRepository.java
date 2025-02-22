package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CustomerCrewRepository extends JpaRepository<CustomerCrew, UUID>, JpaSpecificationExecutor<CustomerCrew> {
}