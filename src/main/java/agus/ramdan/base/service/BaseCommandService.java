package agus.ramdan.base.service;

public interface BaseCommandService<ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    ResultDTO commandCreate(CreateDTO createDTO);
    ResultDTO commandUpdate(DTO_ID id, UpdateDTO updateDTO);
    void commandDelete(DTO_ID delete);
}
