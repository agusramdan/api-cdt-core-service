package agus.ramdan.base.service;

import agus.ramdan.base.utils.BaseSpecifications;
import agus.ramdan.base.utils.BaseSpecificationsBuilder;
import agus.ramdan.base.utils.ChekUtils;
import agus.ramdan.base.utils.OffsetBasedPageRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.dataloader.Try;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public interface BaseQueryAllService<T, DTO> {
    JpaSpecificationExecutor<T> getJpaSpecificationExecutor();

    DTO convertOne(T entity);
    default void exceptionLog(Throwable e , Object object){
        LoggerFactory.getLogger("agus.ramdan.base.service.BaseQueryAllService").error(e.getMessage(),e);
    }
    default List<DTO> getAll(int offset, int limit, String search, String ids) {
        val builder = new BaseSpecificationsBuilder<T>();
        if (StringUtils.hasText(ids)) {
            val list = Arrays.stream(ids.split(","))
                    .map(String::trim) // Menghapus spasi di sekitar angka
                    .map(Long::parseLong) // Mengonversi ke Long
                    .collect(Collectors.toList());
            builder.ids_in(list);
            if (!list.isEmpty()) {
                limit = Math.max(list.size(), limit);
            }
        }
        builder.withSearch(search);
        val spec = builder.build(BaseSpecifications::new);
        val pageable = new OffsetBasedPageRequest(offset, limit);
        val page = getJpaSpecificationExecutor().findAll(spec, pageable);
        ChekUtils.ifEmptyThrow(page);
        return page.getContent().stream().map( t->
                Try.tryCall(()->convertOne(t)).recover(
                        e->{exceptionLog(e,t);
                            return null;
                        }).orElse(null)
        ).filter(d-> d!=null ).collect(Collectors.toList());
    }
}
