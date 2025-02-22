package agus.ramdan.base.service;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.utils.ChekUtils;
import lombok.val;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseQueryOneService<T,DTO,ID>{
    JpaRepository<T,ID> getRepository();

    DTO convert(T entity);
    default DTO getById(ID id)throws ResourceNotFoundException {
        val data = ChekUtils.getOrThrow(getRepository().findById(id), () -> "Data not found for this id :: " + id);
        return convert(data) ;
    }
}
