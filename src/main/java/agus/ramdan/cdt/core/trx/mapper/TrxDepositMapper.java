package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositDenCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositDenQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositDenom;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrxDepositMapper {

    default TrxDepositStatus toTrxDepositStatus(String string) {
        return TrxDepositStatus.valueOf(string);
    }
    @Mapping(target ="auditMetadata", ignore = true )
    TrxDeposit toEntity(TrxDepositCreateDTO dto);

    @Mapping(target = "status", constant = "PREPARE")
    @Mapping(target = "code", expression = "java(qrCode)")
    @Mapping(target = "machine", source = "machine")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auditMetadata", ignore = true)
    TrxDeposit toEntity(TrxDepositCreateDTO dto, Machine machine, QRCode qrCode);

    TrxDepositDenom toEntity(TrxDepositDenCreateDTO dto);
    @Mapping(target = "username", source = "user.username")
    TrxDepositQueryDTO entityToQueryDto(TrxDeposit entity);

    TrxDepositDenQueryDTO entityToQueryDto(TrxDepositDenom entity);

    List<TrxDepositQueryDTO> toDtoList(List<TrxDeposit> entities);
}

