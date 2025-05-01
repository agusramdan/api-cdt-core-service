package agus.ramdan.base.service;

import org.springframework.transaction.annotation.Transactional;

public interface BaseCommandService<ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    @Transactional
    ResultDTO commandCreate(CreateDTO createDTO);
    @Transactional
    ResultDTO commandUpdate(DTO_ID id, UpdateDTO updateDTO);
    @Transactional
    void commandDelete(DTO_ID delete);
}
