package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.cdt.core.master.controller.dto.BankDTO;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.controller.dto.ServiceProductDTO;
import agus.ramdan.cdt.core.master.persistence.domain.*;
import agus.ramdan.cdt.core.trx.controller.dto.BeneficiaryAccountDTO;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceTransactionDTO;
import agus.ramdan.cdt.core.trx.controller.dto.TrxUserDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeUpdateDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCodeType;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface QRCodeMapper {
    String map(UUID source);
    UUID map(String source);
    ServiceProduct map(ServiceProductDTO source);
    ServiceProductDTO map(ServiceProduct source);
    BeneficiaryAccount map(BeneficiaryAccountDTO source);
    BeneficiaryAccountDTO map(BeneficiaryAccount source);
    ServiceTransaction map(ServiceTransactionDTO source);
    ServiceTransactionDTO map(ServiceTransaction source);
    TrxUser map(TrxUserDTO source);
    TrxUserDTO map(CustomerCrew source);
    String map(QRCodeType source);
    Branch map(BranchDTO source);
    BranchDTO map(Branch source);
    Bank map(BankDTO source);
    BankDTO map(Bank source);

    //    @Mapping(source = "beneficiaryAccount.beneficiary_id", target = "beneficiaryAccount.beneficiary_id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "user.customer_id", target = "user.customer_id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "user.customer_crew_id", target = "user.customer_crew_id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "serviceTransaction.id", target = "serviceTransaction.id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "serviceProduct.id", target = "serviceProduct.id", qualifiedByName = "stringToUUID")
    QRCode createDtoToEntity(QRCodeCreateDTO dto);

//    @Mapping(source = "user.customer_id", target = "user.customer_id", qualifiedByName = "uuidToString")
//    @Mapping(source = "user.customer_crew_id", target = "user.customer_crew_id", qualifiedByName = "uuidToString")
//    @Mapping(source = "serviceTransaction.id", target = "serviceTransaction.id", qualifiedByName = "uuidToString")
//    @Mapping(source = "serviceProduct.id", target = "serviceProduct.id", qualifiedByName = "uuidToString")
    QRCodeQueryDTO entityToQueryDto(QRCode entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(QRCodeUpdateDTO dto, @MappingTarget QRCode entity);
}
