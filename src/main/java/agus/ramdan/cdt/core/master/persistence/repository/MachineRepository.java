package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MachineRepository extends JpaRepository<Machine, UUID>, JpaSpecificationExecutor<Machine> {
}