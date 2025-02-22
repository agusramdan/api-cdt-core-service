package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    TrxDepositQueryDTO toDto(TrxDeposit entity);
    List<TrxDepositQueryDTO> toDtoList(List<TrxDeposit> entities);
}
