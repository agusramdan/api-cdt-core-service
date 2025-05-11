package agus.ramdan.cdt.core.master.service.channelcode;

import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeUpdateDTO;
import agus.ramdan.cdt.core.master.mapping.ChannelCodeMapper;
import agus.ramdan.cdt.core.master.persistence.domain.ChannelCode;
import agus.ramdan.cdt.core.master.persistence.repository.ChannelCodeRepository;
import agus.ramdan.cdt.core.master.service.MasterDataEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelCodeCommandService extends MasterDataEventProducer<ChannelCode, String, ChannelCodeQueryDTO, ChannelCodeCreateDTO, ChannelCodeUpdateDTO, String> {

    private final ChannelCodeRepository repository;
    private final ChannelCodeMapper mapper;

    @Override
    public String convertId(String id) {
        return id;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public ChannelCode saveCreate(ChannelCode data) {
        return repository.save(data);
    }

    @Override
    public ChannelCode saveUpdate(ChannelCode data) {
        return repository.save(data);
    }

    @Override
    public ChannelCode convertFromCreateDTO(ChannelCodeCreateDTO dto) {
        return mapper.createDtoToEntity(dto);
    }

    @Override
    public ChannelCode convertFromUpdateDTO(String id, ChannelCodeUpdateDTO dto) {
        ChannelCode channelCode = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel Code not found"));
        mapper.updateEntityFromUpdateDto(dto, channelCode);
        return channelCode;
    }

    @Override
    public ChannelCodeQueryDTO convertToResultDTO(ChannelCode entity) {
        return mapper.entityToQueryDto(entity);
    }
}

