package agus.ramdan.cdt.core.trx.service.qrcode;

import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.QRCodeMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.repository.QRCodeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class QRCodeQueryService implements
        BaseQueryEntityService<QRCode, UUID, QRCodeQueryDTO, String> {

    private final QRCodeRepository repository;
    private final QRCodeMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public QRCodeQueryDTO convertOne(QRCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public QRCodeQueryDTO convert(QRCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    public QRCodeQueryDTO getQRCodeByCode(String code) {
        return findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
    }
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
