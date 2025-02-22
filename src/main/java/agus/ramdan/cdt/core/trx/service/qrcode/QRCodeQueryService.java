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
        BaseQueryEntityService<QRCode, UUID, QRCodeQueryDTO, UUID> {

    private final QRCodeRepository repository;
    private final QRCodeMapper mapper;

    @Override
    public UUID convertId(UUID uuid) {
        return uuid;
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
        return repository.findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
    }
}
