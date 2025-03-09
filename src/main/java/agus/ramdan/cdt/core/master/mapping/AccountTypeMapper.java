package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.accounttype.AccountTypeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.AccountType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

//    @Mapping(source = "id", target = "id", ignore = true)
    AccountType createDtoToEntity(AccountTypeCreateDTO dto);

    AccountTypeQueryDTO entityToQueryDto(AccountType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(AccountTypeUpdateDTO dto, @MappingTarget AccountType entity);
}
