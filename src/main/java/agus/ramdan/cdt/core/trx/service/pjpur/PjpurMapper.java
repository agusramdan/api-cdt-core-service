package agus.ramdan.cdt.core.trx.service.pjpur;

import agus.ramdan.cdt.core.pjpur.controller.dto.BillDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.collect.CollectDTO;
import agus.ramdan.cdt.core.pjpur.controller.dto.deposit.DepositDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigInteger;
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
    @Mapping(source = "serviceTransaction.no", target = "trxid")
    @Mapping(source = "denominations", target = "bills")
    DepositDTO mapDepositDTO(TrxDeposit deposit);
    @Mapping(source = "denomination", target = "denom")
    @Mapping(source = "quantity", target = "qty")
    BillDTO mapBillDTO(TrxDepositDenom trxTransfer);

    @Mapping(source = "machine.code", target = "terminalid")
    @Mapping(source = "trxDateTime", target = "timestamp")
    @Mapping(source = "serviceTransaction.no", target = "trxrefno")
    @Mapping(source = "user.id", target = "accinfo.userid")
    @Mapping(source = "user.username", target = "accinfo.username")
    @Mapping(source = "beneficiaryAccount.accountNumber", target = "accinfo.bankacc")
    @Mapping(source = "beneficiaryAccount.bank.code", target = "accinfo.bankcode")
    @Mapping(constant = "1", target = "batch")
    @Mapping(source = "serviceTransaction.no", target = "trxid")
    @Mapping(source = "denominations", target = "bills")
    DepositDTO mapDepositDTO(TrxDepositPjpur deposit);

    @Mapping(source = "denomination", target = "denom")
    @Mapping(source = "quantity", target = "qty")
    BillDTO mapBillDTO(TrxDepositPjpurDenom trxTransfer);

    @Mapping(source = "machine.code", target = "terminalid")
    @Mapping(source = "cdmTrxDateTime", target = "timestamp")
    @Mapping(constant = "1", target = "batch")
    @Mapping(source = "denominations", target = "bills")
    CollectDTO mapCollectDTO(TrxPickup pickup);

    @Mapping(source = "denomination", target = "denom")
    @Mapping(source = "quantity", target = "qty")
    BillDTO mapBillDTO(TrxPickupDenom trxTransfer);

    default ZonedDateTime mapZonedDateTime(LocalDateTime value){
        return value.atZone(ZoneId.of("Asia/Jakarta"));
    }

    default BigInteger mapBigInteger(String s){
        if (s == null) return null;
        return new BigInteger(s);
    }
}
