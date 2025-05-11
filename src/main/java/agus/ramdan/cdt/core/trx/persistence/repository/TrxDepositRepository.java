package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TrxDepositRepository extends JpaRepositoryImplementation<TrxDeposit, UUID> {
    Optional<TrxDeposit> findByTokenAndSignature(String token, String signature);

    Optional<TrxDeposit> findByTokenAndCdmTrxNoAndCdmTrxDateAndMachine(String token, String cdmTrxNo, LocalDate date, Machine machine);
}