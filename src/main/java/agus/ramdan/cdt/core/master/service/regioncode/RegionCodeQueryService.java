package agus.ramdan.cdt.core.master.service.regioncode;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeQueryDTO;
import agus.ramdan.cdt.core.master.mapping.RegionCodeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.RegionCode;
import agus.ramdan.cdt.core.master.persistence.repository.RegionCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionCodeQueryService implements
        BaseQueryEntityService<RegionCode, String, RegionCodeQueryDTO, String> {

    private final RegionCodeRepository repository;
    private final RegionCodeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public JpaRepository<RegionCode, String> getRepository() {
        return repository;
    }

    @Override
    public RegionCodeQueryDTO convertOne(RegionCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public RegionCodeQueryDTO convert(RegionCode entity) {
        return mapper.entityToQueryDto(entity);
    }
}

