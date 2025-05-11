package agus.ramdan.cdt.core.master.service.accounttype;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeQueryDTO;
import agus.ramdan.cdt.core.master.mapping.AccountTypeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.AccountType;
import agus.ramdan.cdt.core.master.persistence.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountTypeQueryService implements
        BaseQueryEntityService<AccountType, String, AccountTypeQueryDTO, String> {

    private final AccountTypeRepository repository;
    private final AccountTypeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public JpaRepository<AccountType, String> getRepository() {
        return repository;
    }

    @Override
    public AccountTypeQueryDTO convertOne(AccountType entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public AccountTypeQueryDTO convert(AccountType entity) {
        return mapper.entityToQueryDto(entity);
    }
}
