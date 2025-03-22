package agus.ramdan.cdt.core.trx.service.gateway;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GatewayTransferMapper {

    @Mapping(source = "gateway.code", target = "gatewayCode")
    // @Mapping(source = "beneficiaryAccount.account_number", target = "transferType")
    @Mapping(source = "beneficiaryAccount.firstname", target = "destinationAccountFirstname")
    @Mapping(source = "beneficiaryAccount.lastname", target = "destinationAccountLastname")
    @Mapping(source = "beneficiaryAccount.accountNumber", target = "destinationAccount")
    @Mapping(source = "beneficiaryAccount.accountName", target = "destinationAccountName")
    @Mapping(source = "beneficiaryAccount.bank.code", target = "destinationBankCode")
    @Mapping(source = "beneficiaryAccount.regionCode.id", target = "destinationRegionCode")
    @Mapping(source = "beneficiaryAccount.countryCode.id", target = "destinationCountryCode")
    @Mapping(source = "beneficiaryAccount.customerStatus.id", target = "destinationCustomerStatus")
    @Mapping(source = "beneficiaryAccount.customerType.id", target = "destinationCustomerType")
    @Mapping(source = "beneficiaryAccount.accountType.id", target = "destinationAccountType")
    // purposeOfTransaction
    @Mapping(source = "transaction.no", target = "transactionNo")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "trxDate", target = "transferDate")
    @Mapping(source = "description", target = "description")
        //@Mapping(source = "amount", target = "checkName")
    TransferBalanceRequestDTO mapDTO(TrxTransfer transfer);

}
