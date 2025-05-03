package agus.ramdan.cdt.core.trx.service.transaction;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.trx.controller.dto.transaction.ServiceTransactionQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.ServiceTransactionMapper;
import agus.ramdan.cdt.core.trx.mapper.ServiceTransactionMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import agus.ramdan.cdt.core.trx.persistence.repository.ServiceTransactionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class ServiceTransactionQueryService implements
        BaseQueryEntityService<ServiceTransaction, UUID, ServiceTransactionQueryDTO, String> {

    private final ServiceTransactionRepository repository;
    private final ServiceTransactionMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public ServiceTransactionQueryDTO convertOne(ServiceTransaction entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public ServiceTransactionQueryDTO convert(ServiceTransaction entity) {
        return mapper.entityToQueryDto(entity);
    }

//    public ServiceTransactionQueryDTO getServiceTransactionByCode(String code) {
//        return findByCode(code)
//                .map(mapper::entityToQueryDto)
//                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
//    }
//    public QRCode getForRelation(final QRCodeDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
//        final String keyField = key==null?"bank":key;
//        QRCode data = null;
//        if (dto != null) {
//            if (dto.getId() != null) {
//                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
//                    validations.add(ErrorValidation.New("QRCode not found",keyField+".id", dto.getId()));
//                    return null;
//                });
//            } else {
//                data = repository.findByCode(dto.getCode()).orElseGet( () -> {
//                    validations.add(ErrorValidation.New("QRCode not found",keyField+".code", dto.getCode()));
//                    return null;
//                });
//            }
//        }
//        return data;
//    }
}
