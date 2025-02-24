package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.ServiceLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, UUID>, JpaSpecificationExecutor<ServiceLocation> {
}