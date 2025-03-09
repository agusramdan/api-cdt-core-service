package agus.ramdan.cdt.core.master.service.countrycode;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.countrycode.CountryCodeQueryDTO;
import agus.ramdan.cdt.core.master.mapping.CountryCodeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.CountryCode;
import agus.ramdan.cdt.core.master.persistence.repository.CountryCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryCodeQueryService implements
        BaseQueryEntityService<CountryCode, String, CountryCodeQueryDTO, String> {

    private final CountryCodeRepository repository;
    private final CountryCodeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public JpaRepository<CountryCode, String> getRepository() {
        return repository;
    }

    @Override
    public CountryCodeQueryDTO convertOne(CountryCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public CountryCodeQueryDTO convert(CountryCode entity) {
        return mapper.entityToQueryDto(entity);
    }
}
