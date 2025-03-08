package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface BeneficiaryAccountRepository extends JpaRepository<BeneficiaryAccount, UUID>, JpaSpecificationExecutor<BeneficiaryAccount> {

}