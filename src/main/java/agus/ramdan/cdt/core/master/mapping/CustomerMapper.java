package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.CustomerType;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    String map(CustomerType source);

    @Named("mapCustomerType")
    default CustomerType mapCustomerType(String source) {
        if (source == null) return null;
        return CustomerType.valueOf(source);
    }

    // Convert Entity to Query DTO
    CustomerQueryDTO entityToQueryDto(Customer customer);

    // Convert Create DTO to Entity
    @Mapping(source = "customerType", target = "customerType", qualifiedByName = "mapCustomerType")
    @Mapping(target ="auditMetadata", ignore = true )
    Customer createDtoToEntity(CustomerCreateDTO customerCreateDTO);

    // Update existing Entity from Update DTO (null fields ignored)
    @Mapping(source = "customerType", target = "customerType", qualifiedByName = "mapCustomerType")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target ="auditMetadata", ignore = true )
    void updateEntityFromUpdateDto(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer entity);
}
