package agus.ramdan.cdt.core.master.service.bank;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.BankMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Bank;
import agus.ramdan.cdt.core.master.persistence.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankCommandService implements
        BaseCommandEntityService<Bank, UUID, BankQueryDTO, BankCreateDTO, BankUpdateDTO, String> {

    private final BankRepository repository;
    private final BankMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Bank saveCreate(Bank data) {
        return repository.save(data);
    }

    @Override
    public Bank saveUpdate(Bank data) {
        return repository.save(data);
    }

    @Override
    public Bank convertFromCreateDTO(BankCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public Bank convertFromUpdateDTO(String id, BankUpdateDTO dto) {
        Bank bank = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        mapper.updateEntityFromUpdateDto(dto, bank);
        return bank;
    }

    @Override
    public BankQueryDTO convertToResultDTO(Bank entity) {
        return mapper.entityToQueryDto(entity);
    }
}
