package agus.ramdan.cdt.core.master.service.vendor;

import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.VendorMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Vendor;
import agus.ramdan.cdt.core.master.persistence.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorCommandService implements
        BaseCommandEntityService<Vendor, UUID, VendorQueryDTO, VendorCreateDTO, VendorUpdateDTO, String> {

    private final VendorRepository repository;
    private final VendorMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Vendor saveCreate(Vendor data) {
        return repository.save(data);
    }

    @Override
    public Vendor saveUpdate(Vendor data) {
        return repository.save(data);
    }

    @Override
    public Vendor convertFromCreateDTO(VendorCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public Vendor convertFromUpdateDTO(String id, VendorUpdateDTO dto) {
        Vendor vendor = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        mapper.updateEntityFromUpdateDto(dto, vendor);
        return vendor;
    }

    @Override
    public VendorQueryDTO convertToResultDTO(Vendor entity) {
        return mapper.entityToQueryDto(entity);
    }
}
