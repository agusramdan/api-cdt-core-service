package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID>, JpaSpecificationExecutor<Vendor> {
}