package agus.ramdan.cdt.core.master.persistence.repository;

import agus.ramdan.cdt.core.master.persistence.domain.ChannelCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelCodeRepository extends JpaRepository<ChannelCode, String>, JpaSpecificationExecutor<ChannelCode> {
}