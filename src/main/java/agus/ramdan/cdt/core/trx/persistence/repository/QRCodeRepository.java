package agus.ramdan.cdt.core.trx.persistence.repository;

import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepositoryImplementation<QRCode, UUID> {
    Optional<QRCode> findByCode(String code);

}
