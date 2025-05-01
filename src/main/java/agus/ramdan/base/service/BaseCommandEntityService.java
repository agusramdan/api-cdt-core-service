package agus.ramdan.base.service;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorMessage;
import agus.ramdan.base.exception.Errors;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface BaseCommandEntityService<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID> extends
        BaseCommandService<ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    default T getById(ID id) {
        return null;
    }
    default void publishEntityEvent( EventType eventType,T entity) {
        publishDataEvent(new DataEvent(eventType,entity.getClass().getCanonicalName(),entity));
    }
    default void publishDataEvent(DataEvent dataEvent) {
    }
    default ResultDTO commandCreate(CreateDTO createDTO) {
        T data;
        try {
            data = convertFromCreateDTO(createDTO);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Invalid Data For Create", e);
        }
        T newData ;
        try{
            newData = saveCreate(data);
        }catch (BadRequestException e) {
            throw e;
        }catch (DataIntegrityViolationException ex){
            val msg =ErrorMessage.get(ex,"Data Integrity Violation");
            throw new BadRequestException("Data Integrity Violation", new Errors(msg,createDTO));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(),new Errors("Invalid Data For Create",createDTO));
        }
        publishEntityEvent(EventType.CREATE,newData);
        newData = afterCreate(newData, createDTO);
        return convertToResultDTO(newData);
    }

    default T afterCreate(T data, CreateDTO createDTO) {
        return data;
    }

    default ResultDTO commandUpdate(DTO_ID id, UpdateDTO updateDTO) {
        T data;
        try {
            data = convertFromUpdateDTO(id, updateDTO);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(),new Errors("Invalid Data For Update",updateDTO));
        }
        T newData;
        try{
            newData = saveUpdate(data);
        }catch (BadRequestException e) {
            throw e;
        }catch (DataIntegrityViolationException ex){
            val msg =ErrorMessage.get(ex,"Data Integrity Violation");
            throw new BadRequestException("Data Integrity Violation", new Errors(msg,updateDTO));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(),new Errors("Invalid Data For Update",updateDTO));
        }
        publishEntityEvent(EventType.UPDATE,newData);
        newData = afterUpdate(newData, updateDTO);
        return convertToResultDTO(newData);
    }

    default T afterUpdate(T data, UpdateDTO updateDTO) {
        return data;
    }

    default void commandDelete(DTO_ID delete) {
        ID data;
        T entity ;
        try {
            data = convertId(delete);
            entity = getById(data);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Invalid ID");
        }
        delete(data);
        if (entity != null) publishEntityEvent(EventType.DELETE,entity);
    }

    ID convertId(DTO_ID id);

    void delete(ID id);

    T saveCreate(T data);

    T saveUpdate(T data);

    T convertFromCreateDTO(CreateDTO createDTO);

    T convertFromUpdateDTO(DTO_ID id, UpdateDTO createDTO);

    ResultDTO convertToResultDTO(T entity);

}
