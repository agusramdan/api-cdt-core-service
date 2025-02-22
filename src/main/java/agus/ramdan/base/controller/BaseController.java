package agus.ramdan.base.controller;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface BaseController<T, ID> {
    JpaRepositoryImplementation<T, ID> getRepository();
}
