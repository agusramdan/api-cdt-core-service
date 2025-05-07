package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.base.service.QueryDTOMapper;
import agus.ramdan.base.utils.EntityFallbackFactory;
import agus.ramdan.cdt.core.master.controller.dto.CustomerCrewDTO;
import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.cdt.core.master.persistence.domain.CustomerCrew;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class CustomerCrewMapper implements QueryDTOMapper<CustomerCrewQueryDTO, CustomerCrew> {
    @Autowired
    private CustomerMapper customerMapper;
    @Named("mapCustomerDTO")
    public CustomerDTO mapCustomerDTO(Customer source){
        return customerMapper.entityToDto(source);
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerDTO")
    public abstract CustomerCrewDTO updateEntityToDto(CustomerCrew entity,@MappingTarget CustomerCrewDTO target);
    public CustomerCrewDTO entityToDto(CustomerCrew entity){
        entity = EntityFallbackFactory.safe(entity);
        if (entity == null) {
            return null;
        }
        return updateEntityToDto(entity, new CustomerCrewDTO());

    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerDTO")
    public abstract CustomerCrewQueryDTO updateEntityToDto(CustomerCrew entity,@MappingTarget CustomerCrewQueryDTO target);
    public CustomerCrewQueryDTO entityToQueryDto(CustomerCrew entity){
        entity = EntityFallbackFactory.safe(entity);
        if (entity == null) {
            return null;
        }
        return updateEntityToDto(entity, new CustomerCrewQueryDTO());
    }

    //    @Mapping(source = "id", target = "id", ignore = true)
    //@Mapping(source = "customerId", target = "customer.id", qualifiedByName = "stringToUUID")
//    @Mapping(source = "userId", target = "user_id", qualifiedByName = "stringToUUID")
    public abstract CustomerCrew createDtoToEntity(CustomerCrewCreateDTO dto);

//    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    //@Mapping(source = "customer.id", target = "customerId", qualifiedByName = "uuidToString")
        //@Mapping(source = "user_id", target = "userId", qualifiedByName = "uuidToString")
//    @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomerDTO")
//    public abstract CustomerCrewQueryDTO entityToQueryDto(CustomerCrew entity);

//    @Mapping(source = "customer", target = "customer", qualifiedByName = "stringToUUID")
//    @Mapping(source = "user_id", target = "user_id", qualifiedByName = "stringToUUID")
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
