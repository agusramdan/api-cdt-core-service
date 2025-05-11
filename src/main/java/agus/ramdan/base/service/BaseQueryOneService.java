package agus.ramdan.base.service;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.utils.ChekUtils;
import lombok.val;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public interface BaseQueryOneService<T, DTO, ID> {
    JpaRepository<T, ID> getRepository();

    DTO convert(T entity);

    default Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    default DTO getById(ID id) throws ResourceNotFoundException {
        val data = ChekUtils.getOrThrow(findById(id), () -> "Data not found for this id :: " + id);
        return convert(data);
    }

    default Optional<T> findByCode(String code) {
        try {
            Method method = getRepository().getClass().getMethod("findByCode", String.class);
            return (Optional<T>) method.invoke(getRepository(), code);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return Optional.empty();
        }
    }
}