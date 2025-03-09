package agus.ramdan.cdt.core.master.service.countrycode;

import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.CountryCodeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CountryCode;
import agus.ramdan.cdt.core.master.persistence.repository.CountryCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryCodeCommandService implements
        BaseCommandEntityService<CountryCode, String, CountryCodeQueryDTO, CountryCodeCreateDTO, CountryCodeUpdateDTO, String> {

    private final CountryCodeRepository repository;
    private final CountryCodeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public CountryCode saveCreate(CountryCode data) {
        return repository.save(data);
    }

    @Override
    public CountryCode saveUpdate(CountryCode data) {
        return repository.save(data);
    }

    @Override
    public CountryCode convertFromCreateDTO(CountryCodeCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public CountryCode convertFromUpdateDTO(String id, CountryCodeUpdateDTO dto) {
        CountryCode countryCode = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country Code not found"));
        mapper.updateEntityFromUpdateDto(dto, countryCode);
        return countryCode;
    }

    @Override
    public CountryCodeQueryDTO convertToResultDTO(CountryCode entity) {
        return mapper.entityToQueryDto(entity);
    }
}

