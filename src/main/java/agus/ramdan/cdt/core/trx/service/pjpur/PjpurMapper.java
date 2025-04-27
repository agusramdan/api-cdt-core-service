package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.BillDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.collect.CollectDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.deposit.DepositDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositDenom;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface PjpurMapper {

    @Mapping(source = "machine.code", target = "terminalid")
    @Mapping(source = "cdmTrxDateTime", target = "timestamp")
    @Mapping(source = "serviceTransaction.no", target = "trxrefno")
    @Mapping(source = "user.id", target = "accinfo.userid")
    @Mapping(source = "user.username", target = "accinfo.username")
    @Mapping(source = "beneficiaryAccount.accountNumber", target = "accinfo.bankacc")
    @Mapping(source = "beneficiaryAccount.bank.code", target = "accinfo.bankcode")
    @Mapping(constant = "1", target = "batch")
    @Mapping(constant = "serviceTransaction.no", target = "trxid")
    @Mapping(source = "denominations", target = "bills")
    DepositDTO mapDepositDTO(TrxDeposit deposit);

    @Mapping(source = "denomination", target = "denom")
    @Mapping(source = "quantity", target = "qty")
    BillDTO mapBillDTO(TrxDepositDenom trxTransfer);

    CollectDTO mapCollectDTO(TrxPickup pickup);

    default ZonedDateTime mapZonedDateTime(LocalDateTime value){
        return value.atZone(ZoneId.of("Asia/Jakarta"));
    }
    default Long mapLong(String s){
        return Long.parseLong(s);
    }
}
