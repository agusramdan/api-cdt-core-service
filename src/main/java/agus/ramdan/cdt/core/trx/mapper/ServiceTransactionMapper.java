package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.trx.controller.dto.transaction.ServiceTransactionQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceTransactionMapper {

    //@Mapping(target = "username", source = "user.username")
    //@Mapping(target = "denominations", source = "denominations", qualifiedByName = "mapListToTrxDepositDenQueryDTO")
    ServiceTransactionQueryDTO entityToQueryDto(ServiceTransaction entity);


}

