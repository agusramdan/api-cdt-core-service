package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID>, JpaSpecificationExecutor<Branch> {
}