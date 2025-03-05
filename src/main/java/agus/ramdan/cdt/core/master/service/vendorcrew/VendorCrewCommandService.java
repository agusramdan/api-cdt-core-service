package agus.ramdan.cdt.core.master.service.vendorcrew;

import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.VendorCrewMapper;
import agus.ramdan.cdt.core.master.persistence.domain.VendorCrew;
import agus.ramdan.cdt.core.master.persistence.repository.VendorCrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorCrewCommandService implements
        BaseCommandEntityService<VendorCrew, UUID, VendorCrewQueryDTO, VendorCrewCreateDTO, VendorCrewUpdateDTO, String> {

    private final VendorCrewRepository repository;
    private final VendorCrewMapper mapper;

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
        VendorCrew vendorCrew = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Vendor Crew not found"));
        mapper.updateEntityFromUpdateDto(dto, vendorCrew);
        return vendorCrew;
    }

    @Override
    public VendorCrewQueryDTO convertToResultDTO(VendorCrew entity) {
        return mapper.entityToQueryDto(entity);
    }
}

