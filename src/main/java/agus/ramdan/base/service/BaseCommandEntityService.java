package agus.ramdan.base.service;

import agus.ramdan.base.exception.BadRequestException;

public interface BaseCommandEntityService<T,ID,ResultDTO,CreateDTO,UpdateDTO,DTO_ID> extends
        BaseCommandService<ResultDTO,CreateDTO,UpdateDTO,DTO_ID>{

    default ResultDTO commandCreate(CreateDTO createDTO) {
        T data ;
        try{
            data = convertFromCreateDTO(createDTO);
        } catch (BadRequestException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Invalid Data For Create");
        }
        T newData = saveCreate(data);
        newData = afterCreate(newData,createDTO);
        return convertToResultDTO(newData);
    }
    default T afterCreate(T data, CreateDTO createDTO){
        return data;
    }

    default ResultDTO commandUpdate(DTO_ID id,UpdateDTO updateDTO) {
        T data;
        try{
            data = convertFromUpdateDTO(id,updateDTO);
        } catch (BadRequestException e){
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Invalid Data For Update",null,e);
        }
        T newData = saveUpdate(data);
        newData = afterUpdate(newData,updateDTO);
        return convertToResultDTO(newData);
    }
    default T afterUpdate(T data, UpdateDTO updateDTO){
        return data;
    }
    default void commandDelete(DTO_ID delete){
        ID data;
        try{
            data = convertId(delete);
        } catch (BadRequestException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Invalid ID");
        }
        delete(data);
    };
    ID convertId(DTO_ID id);
    void delete(ID id);
    T saveCreate(T data);
    T saveUpdate(T data);
    T convertFromCreateDTO(CreateDTO createDTO);
    T convertFromUpdateDTO(DTO_ID id,UpdateDTO createDTO);
    ResultDTO convertToResultDTO(T entity);

}
