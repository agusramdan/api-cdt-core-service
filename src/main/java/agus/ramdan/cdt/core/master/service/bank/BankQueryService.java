package agus.ramdan.cdt.core.master.service.bank;

import agus.ramdan.base.dto.TID;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.BankDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BankMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Bank;
import agus.ramdan.cdt.core.master.persistence.repository.BankRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankQueryService implements
        BaseQueryEntityService<Bank, UUID, BankQueryDTO, String> {

    private final BankRepository repository;
    private final BankMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public JpaRepository<Bank, UUID> getRepository() {
        return repository;
    }

    @Override
    public BankQueryDTO convertOne(Bank entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public BankQueryDTO convert(Bank entity) {
        return mapper.entityToQueryDto(entity);
    }
    public BankQueryDTO getByCode(String code) {
        return repository.findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Bank Code not found"));
    }

    public Bank getForRelation(final BankDTO bankDTO, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"bank":key;
        Bank data = null;
        if (bankDTO != null) {
            if (bankDTO.getId() != null) {
                data = repository.findById(convertId(bankDTO.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New("Bank not found",keyField+".id", bankDTO.getId()));
                    return null;
                });
            } else {
                data = repository.findByCode(bankDTO.getCode()).orElseGet( () -> {
                    validations.add(ErrorValidation.New("Bank not found",keyField+".code", bankDTO.getCode()));
                    return null;
                });
            }
        }
        return data;
    }

    @Override
    public Bank getForRelation(TID<String> tid, List<ErrorValidation> validations, String key) {
        if (tid instanceof BankDTO){
            return this.getForRelation((BankDTO) tid,validations,key);
        }
        return BaseQueryEntityService.super.getForRelation(tid, validations, key);
    }
}
