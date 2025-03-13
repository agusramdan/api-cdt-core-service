package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.AccountType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    //    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(target ="auditMetadata", ignore = true )
    AccountType createDtoToEntity(AccountTypeCreateDTO dto);

    AccountTypeQueryDTO entityToQueryDto(AccountType entity);

    @Mapping(target ="auditMetadata", ignore = true )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(AccountTypeUpdateDTO dto, @MappingTarget AccountType entity);
}
