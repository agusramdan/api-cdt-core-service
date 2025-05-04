package agus.ramdan.cdt.core.trx.mapper;

import agus.ramdan.base.service.QueryDTOMapper;
import agus.ramdan.base.utils.EntityFallbackFactory;
import agus.ramdan.cdt.core.master.controller.dto.*;
import agus.ramdan.cdt.core.master.controller.dto.beneficiary.BeneficiaryAccountQueryDTO;
import agus.ramdan.cdt.core.master.mapping.BeneficiaryAccountMapper;
import agus.ramdan.cdt.core.master.mapping.CustomerCrewMapper;
import agus.ramdan.cdt.core.master.mapping.CustomerMapper;
import agus.ramdan.cdt.core.master.persistence.domain.*;
import agus.ramdan.cdt.core.trx.controller.dto.ServiceTransactionDTO;
import agus.ramdan.cdt.core.trx.controller.dto.TrxUserDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeUpdateDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCodeType;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class QRCodeMapper implements QueryDTOMapper<QRCodeQueryDTO,QRCode, String > {
    @Autowired
    private CustomerCrewMapper customerCrewMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private BeneficiaryAccountMapper beneficiaryAccountMapper;
    @Named("mapCustomerCrewDTO")
    public CustomerCrewDTO mapCustomerCrewDTO(CustomerCrew source){
        return customerCrewMapper.entityToDto(source);
    }
    @Named("mapCustomerDTO")
    public CustomerDTO mapCustomerDTO(Customer source){
        return customerMapper.entityToDto(source);
    }
    @Named("mapBeneficiaryAccountDTO")
    public BeneficiaryAccountDTO mapBeneficiaryAccountDTO(BeneficiaryAccount source){
        return beneficiaryAccountMapper.entityToDto(source);
    }
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    public abstract ServiceProduct map(ServiceProductDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    public abstract ServiceProductDTO map(ServiceProduct source);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    public abstract BeneficiaryAccount mapBeneficiaryAccount(BeneficiaryAccountDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    public abstract ServiceTransaction map(ServiceTransactionDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    public abstract ServiceTransactionDTO map(ServiceTransaction source);

    @Mapping(source = "customer_crew_id", target = "id", qualifiedByName = "stringToUUID")
    public abstract CustomerCrew map(TrxUserDTO source);

    @Mapping(source = "id", target = "customer_crew_id", qualifiedByName = "uuidToString")
    public abstract TrxUserDTO map(CustomerCrew source);

    public abstract String map(QRCodeType source);

    public QRCodeType mapQRCodeType(String source) {
        if (source == null) return null;
        return QRCodeType.valueOf(source);
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    public abstract Branch map(BranchDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    public abstract BranchDTO map(Branch source);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    public abstract Bank map(BankDTO source);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    public abstract BankDTO map(Bank source);

    //    @Mapping(source = "user.customer_id", target = "user.customer_id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "user.customer_crew_id", target = "user.customer_crew_id", qualifiedByName = "stringToUUID")
    public abstract QRCode createDtoToEntity(QRCodeCreateDTO dto);

    public QRCodeQueryDTO entityToQueryDto(QRCode entity){
        entity = EntityFallbackFactory.safe(entity);
        if (entity == null) {
            return null;
        }
        return updateEntityToDto(new QRCodeQueryDTO(), entity);
    }
    @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerDTO")
    @Mapping(source = "user", target = "user", qualifiedByName = "mapCustomerCrewDTO")
    //@Mapping(source = "serviceTransaction.id", target = "serviceTransaction.id", qualifiedByName = "uuidToString")
    //@Mapping(source = "serviceProduct.id", target = "serviceProduct.id", qualifiedByName = "uuidToString")
    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    @Mapping(source = "beneficiaryAccount", target = "beneficiaryAccount", qualifiedByName = "mapBeneficiaryAccountDTO")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract QRCodeQueryDTO updateEntityToDto(@MappingTarget QRCodeQueryDTO dto,  QRCode entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromUpdateDto(QRCodeUpdateDTO dto, @MappingTarget QRCode entity);

    @Named("uuidToString")
    public String uuidToString(UUID source) {
        if (source == null) return null;
        return source.toString();
    }

    @Named("stringToUUID")
    public UUID stringToUUID(String source) {
        if (source == null) return null;
        return UUID.fromString(source);
    }

}
