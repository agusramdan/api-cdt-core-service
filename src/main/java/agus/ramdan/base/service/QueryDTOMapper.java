package agus.ramdan.base.service;

import org.mapstruct.MappingTarget;

import java.util.UUID;

public interface QueryDTOMapper <QueryDTO, Entity, ID_DTO> {
    QueryDTO entityToQueryDto(Entity entity);
    ID_DTO convertId(UUID id);
    ID_DTO convertId(String id);
}
