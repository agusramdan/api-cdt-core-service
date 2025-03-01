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
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface QRCodeMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    ServiceProduct map(ServiceProductDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    ServiceProductDTO map(ServiceProduct source);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    BeneficiaryAccount mapBeneficiaryAccount(BeneficiaryAccountDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    BeneficiaryAccountDTO map(BeneficiaryAccount source);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    ServiceTransaction map(ServiceTransactionDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    ServiceTransactionDTO map(ServiceTransaction source);

    @Mapping(source = "customer_crew_id", target = "id", qualifiedByName = "stringToUUID")
    CustomerCrew map(TrxUserDTO source);

    @Mapping(source = "id", target = "customer_crew_id", qualifiedByName = "uuidToString")
    TrxUserDTO map(CustomerCrew source);

    String map(QRCodeType source);
    default QRCodeType mapQRCodeType(String source){
        if (source==null) return null;
        return QRCodeType.valueOf(source);
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    Branch map(BranchDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    BranchDTO map(Branch source);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    Bank map(BankDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    BankDTO map(Bank source);

//    @Mapping(source = "user.customer_id", target = "user.customer_id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "user.customer_crew_id", target = "user.customer_crew_id", qualifiedByName = "stringToUUID")
    QRCode createDtoToEntity(QRCodeCreateDTO dto);
    //    @Mapping(source = "user.customer_id", target = "user.customer_id", qualifiedByName = "uuidToString")
//    @Mapping(source = "user.customer_crew_id", target = "user.customer_crew_id", qualifiedByName = "uuidToString")
    //@Mapping(source = "serviceTransaction.id", target = "serviceTransaction.id", qualifiedByName = "uuidToString")
    //@Mapping(source = "serviceProduct.id", target = "serviceProduct.id", qualifiedByName = "uuidToString")
    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    QRCodeQueryDTO entityToQueryDto(QRCode entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(QRCodeUpdateDTO dto, @MappingTarget QRCode entity);

    @Named("uuidToString")
    default String uuidToString(UUID source){
        if(source == null) return null;
        return source.toString();
    }

    @Named("stringToUUID")
    default UUID stringToUUID(String source){
        if(source == null) return null;
        return UUID.fromString(source);
    }

}
