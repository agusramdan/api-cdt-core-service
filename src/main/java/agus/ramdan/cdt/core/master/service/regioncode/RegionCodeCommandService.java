package agus.ramdan.cdt.core.master.service.regioncode;

import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.regioncode.RegionCodeUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.RegionCodeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.RegionCode;
import agus.ramdan.cdt.core.master.persistence.repository.RegionCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionCodeCommandService implements
        BaseCommandEntityService<RegionCode, String, RegionCodeQueryDTO, RegionCodeCreateDTO, RegionCodeUpdateDTO, String> {

    private final RegionCodeRepository repository;
    private final RegionCodeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public RegionCode saveCreate(RegionCode data) {
        return repository.save(data);
    }

    @Override
    public RegionCode saveUpdate(RegionCode data) {
        return repository.save(data);
    }

    @Override
    public RegionCode convertFromCreateDTO(RegionCodeCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public RegionCode convertFromUpdateDTO(String id, RegionCodeUpdateDTO dto) {
        RegionCode regionCode = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region Code not found"));
        mapper.updateEntityFromUpdateDto(dto, regionCode);
        return regionCode;
    }

    @Override
    public RegionCodeQueryDTO convertToResultDTO(RegionCode entity) {
        return mapper.entityToQueryDto(entity);
    }
}

