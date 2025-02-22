package agus.ramdan.cdt.core.master.service.beneficiary;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BeneficiaryAccountMapper;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.repository.BeneficiaryAccountRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeneficiaryAccountQueryService implements
        BaseQueryEntityService<BeneficiaryAccount, UUID, BeneficiaryAccountQueryDTO,UUID> {
    @Getter
    private final BeneficiaryAccountRepository repository;
    private final BeneficiaryAccountMapper mapper;

    @Override
    public BeneficiaryAccountQueryDTO convertOne(BeneficiaryAccount entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public BeneficiaryAccountQueryDTO convert(BeneficiaryAccount entity) {
        return mapper.entityToQueryDto(entity);
    }
    @Override
    public UUID convertId(UUID uuid) {
        return uuid;
    }

}
