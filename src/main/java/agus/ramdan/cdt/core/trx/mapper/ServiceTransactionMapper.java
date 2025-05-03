package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositDenQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.transaction.ServiceTransactionQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceTransactionMapper {

    //@Mapping(target = "username", source = "user.username")
    //@Mapping(target = "denominations", source = "denominations", qualifiedByName = "mapListToTrxDepositDenQueryDTO")
    ServiceTransactionQueryDTO entityToQueryDto(ServiceTransaction entity);


}

