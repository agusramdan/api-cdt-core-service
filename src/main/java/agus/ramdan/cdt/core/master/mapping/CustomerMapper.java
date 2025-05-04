package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.CustomerDTO;
import agus.ramdan.cdt.core.master.controller.dto.CustomerType;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import agus.ramdan.base.utils.EntityFallbackFactory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    public abstract String map(CustomerType source);

    @Named("mapCustomerType")
    public CustomerType mapCustomerType(String source) {
        if (source == null) return null;
        return CustomerType.valueOf(source);
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract CustomerDTO updateEntityToDto(@MappingTarget CustomerDTO dto, Customer entity);

    public  CustomerDTO entityToDto(Customer entity){
        entity = EntityFallbackFactory.safe(entity);
        return  entity == null ? null: updateEntityToDto(new CustomerDTO(), entity);
    }
    // Convert Entity to Query DTO
//    @Mapping(source = "customerType", target = "customerType", qualifiedByName = "mapCustomerType")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract CustomerQueryDTO updateEntityToDto(@MappingTarget CustomerQueryDTO customerUpdateDTO, Customer entity);
    public CustomerQueryDTO entityToQueryDto(Customer entity){
        entity = EntityFallbackFactory.safe(entity);
        return (entity == null) ? null: updateEntityToDto(new CustomerQueryDTO(), entity);
    }

    // Convert Create DTO to Entity
    @Mapping(source = "customerType", target = "customerType", qualifiedByName = "mapCustomerType")
    public abstract Customer createDtoToEntity(CustomerCreateDTO customerCreateDTO);

    // Update existing Entity from Update DTO (null fields ignored)
    @Mapping(source = "customerType", target = "customerType", qualifiedByName = "mapCustomerType")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromUpdateDto(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer entity);
}
