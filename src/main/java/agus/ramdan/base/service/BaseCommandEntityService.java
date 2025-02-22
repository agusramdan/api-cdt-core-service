package agus.ramdan.base.service;

public interface BaseCommandEntityService<T,ID,ResultDTO,CreateDTO,UpdateDTO,DTO_ID> extends
        BaseCommandService<ResultDTO,CreateDTO,UpdateDTO,DTO_ID>{

    default ResultDTO commandCreate(CreateDTO createDTO) {
        T data = convertFromCreateDTO(createDTO);
        T newData = saveCreate(data);
        return convertToResultDTO(newData);
    }

    default ResultDTO commandUpdate(DTO_ID id,UpdateDTO updateDTO) {
        T data = convertFromUpdateDTO(id,updateDTO);
        T newData = saveUpdate(data);
        return convertToResultDTO(newData);
    }

    default void commandDelete(DTO_ID delete){
        delete(convertId(delete));
    };
    ID convertId(DTO_ID id);
    void delete(ID id);
    T saveCreate(T data);
    T saveUpdate(T data);
    T convertFromCreateDTO(CreateDTO createDTO);
    T convertFromUpdateDTO(DTO_ID id,UpdateDTO createDTO);
    ResultDTO convertToResultDTO(T entity);

}
