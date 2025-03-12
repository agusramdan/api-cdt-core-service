package agus.ramdan.cdt.core.master.service.channelcode;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeQueryDTO;
import agus.ramdan.cdt.core.master.mapping.ChannelCodeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ChannelCode;
import agus.ramdan.cdt.core.master.persistence.repository.ChannelCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelCodeQueryService implements
        BaseQueryEntityService<ChannelCode, String, ChannelCodeQueryDTO, String> {

    private final ChannelCodeRepository repository;
    private final ChannelCodeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public JpaRepository<ChannelCode, String> getRepository() {
        return repository;
    }

    @Override
    public ChannelCodeQueryDTO convertOne(ChannelCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public ChannelCodeQueryDTO convert(ChannelCode entity) {
        return mapper.entityToQueryDto(entity);
    }
}
