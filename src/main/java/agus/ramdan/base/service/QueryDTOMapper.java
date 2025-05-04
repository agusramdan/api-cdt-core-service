package agus.ramdan.base.service;

import org.mapstruct.Named;

import java.util.UUID;

public interface QueryDTOMapper <QueryDTO, Entity> {
    QueryDTO entityToQueryDto(Entity entity);
    @Named("uuidToString")
    default String uuidToString(UUID source) {
        if (source == null) return null;
        return source.toString();
    }

    @Named("stringToUUID")
    default UUID stringToUUID(String source) {
        if (source == null) return null;
        return UUID.fromString(source);
    }
}
