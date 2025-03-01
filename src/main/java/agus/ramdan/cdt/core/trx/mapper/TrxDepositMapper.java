package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositDenCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositDenResponseDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositDenom;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrxDepositMapper {

    default TrxDepositStatus toTrxDepositStatus(String string){
        return TrxDepositStatus.valueOf(string);
    }

    TrxDeposit toEntity(TrxDepositCreateDTO dto);
    TrxDepositDenom toEntity(TrxDepositDenCreateDTO dto);

    TrxDepositQueryDTO entityToQueryDto(TrxDeposit entity);
    TrxDepositDenResponseDTO entityToQueryDto(TrxDepositDenom entity);

    List<TrxDepositQueryDTO> toDtoList(List<TrxDeposit> entities);
}

