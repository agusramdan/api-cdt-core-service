package agus.ramdan.cdt.core.master.service.vendorcrew;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.VendorCrewMapper;
import agus.ramdan.cdt.core.master.persistence.domain.VendorCrew;
import agus.ramdan.cdt.core.master.persistence.repository.VendorCrewRepository;
import agus.ramdan.cdt.core.master.service.vendor.VendorQueryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorCrewCommandService implements
        BaseCommandEntityService<VendorCrew, UUID, VendorCrewQueryDTO, VendorCrewCreateDTO, VendorCrewUpdateDTO, String> {

    private final VendorCrewRepository repository;
    private final VendorCrewMapper mapper;
    private final VendorQueryService vendorQueryService;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public VendorCrew saveCreate(VendorCrew data) {
        return repository.save(data);
    }

    @Override
    public VendorCrew saveUpdate(VendorCrew data) {
        return repository.save(data);
    }

    @Override
    public VendorCrew convertFromCreateDTO(VendorCrewCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public VendorCrew convertFromUpdateDTO(String id, VendorCrewUpdateDTO dto) {
        val entity = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Crew not found"));
        val validations = new ArrayList<ErrorValidation>();
        vendorQueryService.relation(dto.getVendorId(), d -> ErrorValidation.add(validations, "Vendor not found", "vendor_id", d)).ifPresent(entity::setVendor);
        BadRequestException.ThrowWhenError("Validation error", validations);
        mapper.updateEntityFromUpdateDto(dto, entity);
        return entity;
    }

    @Override
    public VendorCrewQueryDTO convertToResultDTO(VendorCrew entity) {
        return mapper.entityToQueryDto(entity);
    }
}

