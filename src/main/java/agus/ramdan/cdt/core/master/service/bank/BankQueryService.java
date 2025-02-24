package agus.ramdan.cdt.core.master.service.bank;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BankMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Bank;
import agus.ramdan.cdt.core.master.persistence.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
}
