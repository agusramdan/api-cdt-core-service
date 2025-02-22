package agus.ramdan.base.service;

import agus.ramdan.base.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

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
        return BaseQueryOneService.super.getById(convertId(dtoId));
    }
    DTO convertOne(T entity);

    JpaRepository<T, ID> getRepository();

    DTO convert(T entity);



}
