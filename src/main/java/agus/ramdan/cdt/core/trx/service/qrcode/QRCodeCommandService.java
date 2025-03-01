package agus.ramdan.cdt.core.trx.service.qrcode;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.base.service.BaseCommandService;
import agus.ramdan.cdt.core.master.controller.dto.BranchDTO;
import agus.ramdan.cdt.core.master.persistence.domain.Branch;
import agus.ramdan.cdt.core.master.persistence.repository.BranchRepository;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.QRCodeMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.repository.QRCodeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRCodeCommandService implements
        BaseCommandService<QRCodeQueryDTO, QRCodeCreateDTO, QRCodeUpdateDTO, String>,
        BaseCommandEntityService<QRCode, UUID, QRCodeQueryDTO, QRCodeCreateDTO, QRCodeUpdateDTO, String> {

    @Getter
    private final QRCodeRepository repository;
    private final QRCodeMapper mapper;
    private final RandomStringGenerator generator;
    private final BranchQueryService branchQueryService;

    public void validateActiveStatus(QRCode entity) {
       // TODO Tambahkan validasi bila konsisi status entity aktif
    }

    @Override
    public QRCode saveCreate(QRCode entity) {
        var generate_code = !StringUtils.hasText(entity.getCode());
        while (generate_code) {
            entity.setCode(generator.generateRandomString());
            generate_code = repository.findByCode(entity.getCode()).isPresent();
        }
        validateActiveStatus(entity);
        return repository.save(entity);
    }

    @Override
    public QRCode saveUpdate(QRCode entity) {
        var generate_code = !StringUtils.hasText(entity.getCode());
        while (generate_code) {
            entity.setCode(generator.generateRandomString());
            generate_code = repository.findByCode(entity.getCode()).isPresent();
        }
        validateActiveStatus(entity);
        return repository.save(entity);
    }

    @Override
    public QRCode convertFromCreateDTO(QRCodeCreateDTO dto) {
        val validations = new ArrayList<ErrorValidation>();

        if(StringUtils.hasText(dto.getCode()))
            repository.findByCode(dto.getCode())
                    .ifPresent(qrCode -> {
                        validations.add(ErrorValidation.New("QR Code already exists", "code", qrCode.getCode()));
                    });
        val qrCode = mapper.createDtoToEntity(dto);
        qrCode.setBranch(branchQueryService.getRelation(dto.getBranch(), validations, "branch"));
        if(validations.size() > 0) {
            throw new BadRequestException(
                    "Validation error",
                    validations.toArray(new ErrorValidation[validations.size()])
            );
        }
        return qrCode;
    }

    @Override
    public QRCode convertFromUpdateDTO(String id, QRCodeUpdateDTO dto) {
        QRCode qrCode = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
        mapper.updateEntityFromUpdateDto(dto, qrCode);
        return qrCode;
    }

    @Override
    public QRCodeQueryDTO convertToResultDTO(QRCode entity) {
        return mapper.entityToQueryDto(entity);
    }


    public void delete(UUID id) {
        repository.deleteById(id);
    }
    public UUID convertId(String id) {
        return UUID.fromString(id);
    }
}
