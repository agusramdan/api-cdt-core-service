package agus.ramdan.cdt.core.trx.service.pickup;

import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxPickupMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxPickup;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxPickupRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class TrxPickupQueryService implements
        BaseQueryEntityService<TrxPickup, UUID, TrxPickupQueryDTO, String> {

    private final TrxPickupRepository repository;
    private final TrxPickupMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public TrxPickupQueryDTO convertOne(TrxPickup entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public TrxPickupQueryDTO convert(TrxPickup entity) {
        return mapper.entityToQueryDto(entity);
    }

//    public TrxPickupQueryDTO getTrxPickupByCode(String code) {
//        return repository.findByCode(code)
//                .map(mapper::entityToQueryDto)
//                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
//    }
//    public TrxPickup getForRelation(final TrxPickupDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
//        final String keyField = key==null?"bank":key;
//        TrxPickup data = null;
//        if (dto != null) {
//            if (dto.getId() != null) {
//                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
//                    validations.add(ErrorValidation.New("TrxPickup not found",keyField+".id", dto.getId()));
//                    return null;
//                });
//            } else {
//                data = repository.findByCode(dto.getCode()).orElseGet( () -> {
//                    validations.add(ErrorValidation.New("TrxPickup not found",keyField+".code", dto.getCode()));
//                    return null;
//                });
//            }
//        }
//        return data;
//    }
}
