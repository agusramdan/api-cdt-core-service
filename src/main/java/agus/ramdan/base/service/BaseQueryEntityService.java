package agus.ramdan.base.service;

import agus.ramdan.base.dto.TID;
import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

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

    default T getForRelation(final TID<DTO_ID> tid, @NotNull final List<ErrorValidation> validations, String key) {
        T data = null;
        if (tid != null) {
            final String keyField = key==null?tid.getClass().getSimpleName():key;
            if (tid.getId() != null) {
                data = getRepository().findById(convertId(tid.getId())).orElseGet(() -> {
                    validations.add(ErrorValidation.New(tid.getClass().getSimpleName().replace("DTO","")+" not found",keyField+".id", tid.getId()));
                    return null;
                });
            }
        }
        return data;
    }
    default Optional<T> relation(final TID<DTO_ID> tid, @NotNull final List<ErrorValidation> validations, String key) {
        if (tid !=null) {
            try {
                return Optional.ofNullable(getForRelation(tid, validations, key));
            } catch (Exception e) {
                validations.add(ErrorValidation.New(e.getMessage(), key + ".id", tid.getId()));
            }
        }
        return Optional.empty();
    }
}
