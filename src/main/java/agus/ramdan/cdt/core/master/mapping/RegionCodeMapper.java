package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.RegionCode;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RegionCodeMapper {
    //    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(target ="auditMetadata", ignore = true )
    RegionCode createDtoToEntity(RegionCodeCreateDTO dto);

    RegionCodeQueryDTO entityToQueryDto(RegionCode entity);

    @Mapping(target ="auditMetadata", ignore = true )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(RegionCodeUpdateDTO dto, @MappingTarget RegionCode entity);
}
