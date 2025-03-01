package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.bank.BankCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Bank;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BankMapper {

    @Mapping(target = "id", ignore = true)
    Bank createDtoToEntity(BankCreateDTO dto);

    //@Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    BankQueryDTO entityToQueryDto(Bank entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "auditMetadata", ignore = true)
    void updateEntityFromUpdateDto(BankUpdateDTO dto, @MappingTarget Bank entity);

    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
