package agus.ramdan.cdt.core.master.mapping;

import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.customer.CustomerUpdateDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Convert Entity to Query DTO
    CustomerQueryDTO entityToQueryDto(Customer customer);

    // Convert Create DTO to Entity
    Customer createDtoToEntity(CustomerCreateDTO customerCreateDTO);

    // Update existing Entity from Update DTO (null fields ignored)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateDto(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer entity);
}
