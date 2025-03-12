package agus.ramdan.cdt.core.master.service.accounttype;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.AccountTypeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.AccountType;
import agus.ramdan.cdt.core.master.persistence.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountTypeCommandService implements
        BaseCommandEntityService<AccountType, String, AccountTypeQueryDTO, AccountTypeCreateDTO, AccountTypeUpdateDTO, String> {

    private final AccountTypeRepository repository;
    private final AccountTypeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public AccountType saveCreate(AccountType data) {
        return repository.save(data);
    }

    @Override
    public AccountType saveUpdate(AccountType data) {
        return repository.save(data);
    }

    @Override
    public AccountType convertFromCreateDTO(AccountTypeCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public AccountType convertFromUpdateDTO(String id, AccountTypeUpdateDTO dto) {
        AccountType accountType = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account Type not found"));
        mapper.updateEntityFromUpdateDto(dto, accountType);
        return accountType;
    }

    @Override
    public AccountTypeQueryDTO convertToResultDTO(AccountType entity) {
        return mapper.entityToQueryDto(entity);
    }
}
