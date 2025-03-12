package agus.ramdan.cdt.core.trx.service.qrcode;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseCommandEntityService;
import agus.ramdan.base.service.BaseCommandService;
import agus.ramdan.cdt.core.master.service.beneficiary.BeneficiaryAccountQueryService;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import agus.ramdan.cdt.core.master.service.customercrew.CustomerCrewQueryService;
import agus.ramdan.cdt.core.master.service.product.ServiceProductQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.QRCodeMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCodeStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCodeType;
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
    private final ServiceProductQueryService serviceProductQueryService;
    private final BeneficiaryAccountQueryService beneficiaryAccountQueryService;
    private final CustomerCrewQueryService customerCrewQueryService;

    public void validateActiveStatus(QRCode entity, List<ErrorValidation> validations) {
        // TODO Tambahkan validasi bila konsisi status entity aktif
    }

    @Override
    public QRCode saveCreate(QRCode entity) {
        var generate_code = !StringUtils.hasText(entity.getCode());
        while (generate_code) {
            entity.setCode(generator.generateRandomString());
            generate_code = repository.findByCode(entity.getCode()).isPresent();
        }
        return repository.save(entity);
    }

    @Override
    public QRCode saveUpdate(QRCode entity) {
        var generate_code = !StringUtils.hasText(entity.getCode());
        while (generate_code) {
            entity.setCode(generator.generateRandomString());
            generate_code = repository.findByCode(entity.getCode()).isPresent();
        }
        return repository.save(entity);
    }

    public QRCode useCode(QRCode entity) {
        if (entity.isActive() && entity.getType().equals(QRCodeType.SINGEL_TRX_USE)) {
            entity.setStatus(QRCodeStatus.INACTIVE);
        }
        return repository.save(entity);
    }

    @Override
    public QRCode convertFromCreateDTO(QRCodeCreateDTO dto) {
        val validations = new ArrayList<ErrorValidation>();
        if (StringUtils.hasText(dto.getCode()))
            repository.findByCode(dto.getCode()).ifPresent(qrCode -> ErrorValidation.add(validations, "QR Code already exists", "code", qrCode.getCode()));
        val entity = mapper.createDtoToEntity(dto);
        branchQueryService.relation(dto.getBranch(), validations, "branch").ifPresent(entity::setBranch);
        serviceProductQueryService.relation(dto.getServiceProduct(), validations, "service_product").ifPresent(entity::setServiceProduct);
        beneficiaryAccountQueryService.relation(dto.getBeneficiaryAccount(), validations, "beneficiary_account").ifPresent(entity::setBeneficiaryAccount);
        customerCrewQueryService.relation(dto.getUser(), validations, "user").ifPresent(entity::setUser);
        BadRequestException.ThrowWhenError("Validation error", validations);
        return entity;
    }

    @Override
    public QRCode convertFromUpdateDTO(String id, QRCodeUpdateDTO dto) {

        QRCode entity = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
        mapper.updateEntityFromUpdateDto(dto, entity);
        val validations = new ArrayList<ErrorValidation>();
        branchQueryService.relation(dto.getBranch(), validations, "branch").ifPresent(entity::setBranch);
        serviceProductQueryService.relation(dto.getServiceProduct(), validations, "service_product").ifPresent(entity::setServiceProduct);
        beneficiaryAccountQueryService.relation(dto.getBeneficiaryAccount(), validations, "beneficiary_account").ifPresent(entity::setBeneficiaryAccount);
        customerCrewQueryService.relation(dto.getUser(), validations, "user").ifPresent(entity::setUser);
        BadRequestException.ThrowWhenError("Validation error", validations);
        return entity;
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
