package agus.ramdan.cdt.core.master.service.beneficiary;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BeneficiaryAccountMapper;
import agus.ramdan.cdt.core.master.persistence.domain.BeneficiaryAccount;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import agus.ramdan.cdt.core.master.persistence.repository.BeneficiaryAccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeneficiaryAccountQueryService implements
        BaseQueryEntityService<BeneficiaryAccount, UUID, BeneficiaryAccountQueryDTO,String> {
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
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    public BeneficiaryAccount getForRelation(final BeneficiaryAccountDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"branch":key;
        BeneficiaryAccount data = null;
        if (dto != null) {
            if (dto.getId() != null) {
                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New("Beneficiary Account not found",keyField+".id", dto.getId()));
                    return null;
                });
            }
//            else {
//                data = repository.findByCode(dto.getCode()).orElseGet( () -> {
//                    validations.add(ErrorValidation.New("Beneficiary Account not found",keyField+".code", dto.getCode()));
//                    return null;
//                });
//            }
        }
        return data;
    }
}
