package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.RegionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegionCodeRepository extends JpaRepository<RegionCode, String>, JpaSpecificationExecutor<RegionCode> {
}