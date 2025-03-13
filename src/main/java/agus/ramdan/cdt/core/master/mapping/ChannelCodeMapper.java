package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.ChannelCode;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ChannelCodeMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    @Mapping(target ="auditMetadata", ignore = true )
    ChannelCode createDtoToEntity(ChannelCodeCreateDTO dto);

    ChannelCodeQueryDTO entityToQueryDto(ChannelCode entity);

    @Mapping(target ="auditMetadata", ignore = true )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(ChannelCodeUpdateDTO dto, @MappingTarget ChannelCode entity);
}
