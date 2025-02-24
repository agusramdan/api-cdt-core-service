package agus.ramdan.cdt.core.master.service.vendor;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.vendor.VendorQueryDTO;
import agus.ramdan.cdt.core.master.mapping.VendorMapper;
import agus.ramdan.cdt.core.master.persistence.domain.Vendor;
import agus.ramdan.cdt.core.master.persistence.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorQueryService implements
        BaseQueryEntityService<Vendor, UUID, VendorQueryDTO, String> {

    private final VendorRepository repository;
    private final VendorMapper mapper;

    @Override
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }

    @Override
    public JpaRepository<Vendor, UUID> getRepository() {
        return repository;
    }

    @Override
    public VendorQueryDTO convertOne(Vendor entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public VendorQueryDTO convert(Vendor entity) {
        return mapper.entityToQueryDto(entity);
    }
}
