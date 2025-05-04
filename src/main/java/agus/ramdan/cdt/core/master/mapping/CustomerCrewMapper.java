package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.CustomerCrewDTO;
import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import agus.ramdan.base.utils.EntityFallbackFactory;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class CustomerCrewMapper {
    @Autowired
    private CustomerMapper customerMapper;
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityToDto(CustomerCrew entity,@MappingTarget CustomerCrewDTO target);
    public CustomerCrewDTO entityToDto(CustomerCrew entity){
        if (entity == null) {
            return null;
        }
        entity = EntityFallbackFactory.safe(entity);
        CustomerCrewDTO dto = new CustomerCrewDTO();
        updateEntityToDto(entity, dto);
        return dto;
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityToDto(CustomerCrew entity,@MappingTarget CustomerCrewQueryDTO target);
    public CustomerCrewQueryDTO entityTQueryDto(CustomerCrew entity){
        if (entity == null) {
            return null;
        }
        entity = EntityFallbackFactory.safe(entity);
        CustomerCrewQueryDTO dto = new CustomerCrewQueryDTO();
        updateEntityToDto(entity, dto);
        return dto;
    }

    //    @Mapping(source = "id", target = "id", ignore = true)
    //@Mapping(source = "customerId", target = "customer.id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    public abstract CustomerCrew createDtoToEntity(CustomerCrewCreateDTO dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    //@Mapping(source = "customer.id", target = "customerId", qualifiedByName = "uuidToString")
        //@Mapping(source = "user_id", target = "userId", qualifiedByName = "uuidToString")
    @Mapping(source = "customer", target = "customer", ignore = true)
    public abstract CustomerCrewQueryDTO entityToQueryDto(CustomerCrew entity);

    public CustomerDTO entityToCustomerDTO(Customer entity){
        return customerMapper.entityToDto(entity);
    }
//    @BeanMapping
//    public void handleException(@MappingTarget CustomerCrewQueryDTO customerCrewQueryDTO, CustomerCrew entity) {
//        customerCrewQueryDTO.setCustomer(customerMapper.entityToDto(entity.getCustomer()));
//
//    }

    //@Mapping(source = "customerId", target = "customer.id", qualifiedByName = "stringToUUID")
    //@Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    public abstract  void updateEntityFromUpdateDto(CustomerCrewUpdateDTO dto, @MappingTarget CustomerCrew entity);

    @Named("stringToUUID")
    public UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    public String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
