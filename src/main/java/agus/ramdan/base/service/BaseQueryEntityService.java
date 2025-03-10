package agus.ramdan.base.service;

import agus.ramdan.base.dto.CodeOrID;
import agus.ramdan.base.dto.TID;
import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface BaseQueryEntityService<T,ID,DTO,DTO_ID> extends
        BaseQueryService<DTO,DTO_ID>,
        BaseQueryAllService<T, DTO>,
        BaseQueryOneService<T, DTO, ID>
{
    ID convertId(DTO_ID id);

    @Override
    default JpaSpecificationExecutor<T> getJpaSpecificationExecutor() {
        return (JpaSpecificationExecutor<T>) getRepository();
    }
    @Override
    default List<DTO> getAll(int offset, int limit, String search, String ids) {
        return BaseQueryAllService.super.getAll(offset, limit, search, ids);
    }
    default DTO getByDtoId(DTO_ID dtoId) throws ResourceNotFoundException{
        ID data;
        try{
            data = convertId(dtoId);
        } catch (BadRequestException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Invalid ID");
        }
        return BaseQueryOneService.super.getById(data);
    }
    DTO convertOne(T entity);

    JpaRepository<T, ID> getRepository();

    DTO convert(T entity);

    default T getForRelation(final CodeOrID<DTO_ID> dto, @NotNull final List<ErrorValidation> validations, String key) {
        final String keyField = key==null?"branch":key;
        T data = null;
        if (dto != null) {
            String name = dto.getClass().getSimpleName().replace("DTO","");
            if (dto.getId() != null) {
                data = findById(convertId(dto.getId())).orElseGet(() ->ErrorValidation.add(validations,name+"not found",keyField+".id", dto.getId()));
            } else {
                data = findByCode(dto.getCode()).orElseGet(() ->ErrorValidation.add(validations,name+" not found",keyField+".code", dto.getCode()));
            }
        }
        return data;
    }
    default T getForRelation(final TID<DTO_ID> tid, @NotNull final List<ErrorValidation> validations, String key) {
        T data = null;
        if (tid != null) {
            final String keyField = key==null?tid.getClass().getSimpleName():key;
            if (tid.getId() != null) {
                data = findById(convertId(tid.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New(tid.getClass().getSimpleName().replace("DTO","")+" not found",keyField+".id", tid.getId()));
                    return null;
                });
            }
        }
        return data;
    }
    default Optional<T> relation(final DTO_ID id, Function<DTO_ID,T> whenError) {
        T data = null;
        if (id !=null) {
            try {
                data = findById(convertId(id)).orElseGet(()->whenError.apply(id));
            } catch (Exception e) {
                data = whenError.apply(id);
            }
        }
        return Optional.ofNullable(data);
    }
    default Optional<T> relation(final TID<DTO_ID> tid, @NotNull final List<ErrorValidation> validations, String key) {
        if (tid !=null) {
            try {
                return Optional.ofNullable(getForRelation(tid, validations, key));
            } catch (Exception e) {
                ErrorValidation.add(validations,e.getMessage(), key + ".id", tid.getId());
            }
        }
        return Optional.empty();
    }
    default Optional<T> relation(CodeOrID<DTO_ID> dto, @NotNull final List<ErrorValidation> validations, String key) {
        if (dto !=null) {
            try {
                return Optional.ofNullable(getForRelation(dto, validations, key));
            } catch (Exception e) {
                ErrorValidation.add(validations,e.getMessage(), key + ".id", dto.getId());
            }
        }
        return Optional.empty();
    }
}
