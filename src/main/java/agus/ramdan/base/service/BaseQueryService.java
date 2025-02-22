package agus.ramdan.base.service;

import agus.ramdan.base.exception.ResourceNotFoundException;

import java.util.List;

public interface BaseQueryService<DTO,DTO_ID>{

    List<DTO> getAll(int offset, int limit, String search, String ids );
    DTO getByDtoId(DTO_ID id)throws ResourceNotFoundException;
}
