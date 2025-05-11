package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.ChannelCode;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ChannelCodeMapper {

    //@Mapping(source = "id", target = "id", ignore = true)
    ChannelCode createDtoToEntity(ChannelCodeCreateDTO dto);

    ChannelCodeQueryDTO entityToQueryDto(ChannelCode entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(ChannelCodeUpdateDTO dto, @MappingTarget ChannelCode entity);
}
