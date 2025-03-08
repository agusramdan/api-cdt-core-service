package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface TrxDepositRepository extends JpaRepositoryImplementation<TrxDeposit, UUID> {
    Optional<TrxDeposit> findByTokenAndSignature(String token, String signature);
}