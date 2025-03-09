package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.CountryCode;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CountryCodeMapper {

//    @Mapping(source = "id", target = "id", ignore = true)
    CountryCode createDtoToEntity(CountryCodeCreateDTO dto);

    CountryCodeQueryDTO entityToQueryDto(CountryCode entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(CountryCodeUpdateDTO dto, @MappingTarget CountryCode entity);
}
