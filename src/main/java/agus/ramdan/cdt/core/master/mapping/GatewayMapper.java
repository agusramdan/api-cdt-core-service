package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.bank.BankCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.bank.BankUpdateDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.gateway.GatewayUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Bank;
import agus.ramdan.cdt.core.master.persistence.domain.Gateway;
import org.mapstruct.*;

import java.util.UUID;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GatewayMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "partnerId", target = "partner.id", qualifiedByName = "stringToUUID")
    Gateway createDtoToEntity(GatewayCreateDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "partner.id", target = "partnerId")
    GatewayQueryDTO entityToQueryDto(Gateway entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "partnerId", target = "partner.id")
    void updateEntityFromUpdateDto(GatewayUpdateDTO dto, @MappingTarget Gateway entity);

    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
