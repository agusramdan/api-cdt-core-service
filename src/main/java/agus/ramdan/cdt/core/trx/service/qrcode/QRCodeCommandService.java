package agus.ramdan.cdt.core.trx.service.qrcode;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.service.beneficiary.BeneficiaryAccountQueryService;
import agus.ramdan.cdt.core.master.service.branch.BranchQueryService;
import agus.ramdan.cdt.core.master.service.customer.CustomerQueryService;
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
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducer;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static agus.ramdan.cdt.core.trx.persistence.domain.QRCodeType.MULTIPLE_TRX_USE;
import static agus.ramdan.cdt.core.trx.persistence.domain.QRCodeType.SINGLE_TRX_USE;

@Service
@RequiredArgsConstructor
public class QRCodeCommandService extends TrxDataEventProducer<QRCode, UUID, QRCodeQueryDTO, QRCodeCreateDTO, QRCodeUpdateDTO, String> {

    @Getter
    private final QRCodeRepository repository;
    private final QRCodeMapper mapper;
    private final RandomStringGenerator generator;
    private final BranchQueryService branchQueryService;
    private final ServiceProductQueryService serviceProductQueryService;
    private final BeneficiaryAccountQueryService beneficiaryAccountQueryService;
    private final CustomerCrewQueryService customerCrewQueryService;
    private final CustomerQueryService customerQueryService;
    private final QRCodeQueryService qrCodeQueryService;

    public boolean repairCode(@NotNull QRCode entity) {
        boolean repair = false;


        val product = entity.getServiceProduct();
        if (product!=null){
            switch (product.getServiceRuleConfig()) {
                case DEPOSIT -> {
                    val ben = entity.getBeneficiaryAccount();
                    if (ben != null && ben.getCustomer() != null && entity.getCustomer()==null) {
                        entity.setCustomer(ben.getCustomer());
                        repair = true;
                    }
                    val crew = entity.getUser();
                    if (crew != null && crew.getCustomer() != null && entity.getCustomer()==null) {
                        entity.setCustomer(crew.getCustomer());
                        repair = true;
                    }
                }
                case COLLECTION->{
                    val crew = entity.getVendorCrew();
                    if (crew !=null && crew.getVendor()!= null && entity.getVendor()==null){
                        entity.setVendor(crew.getVendor());
                        repair = true;
                    }
                }
            }
            if (entity.getType()==null || (!MULTIPLE_TRX_USE.equals(entity.getType()) && !SINGLE_TRX_USE.equals(entity.getType()))) {
                entity.setType(switch (product.getQrRuleConfig()) {
                    case SINGLE_USAGE -> QRCodeType.SINGLE_TRX_USE;
                    case MULTIPLE_USAGE -> QRCodeType.MULTIPLE_TRX_USE;
                    default -> entity.getType();
                });
                repair = true;
            }
        }
        if (entity.getType()!=null && !MULTIPLE_TRX_USE.equals(entity.getType()) && !SINGLE_TRX_USE.equals(entity.getType())) {
            entity.setType(SINGLE_TRX_USE);
            repair = true;
        }
        return repair;
    }
    public void validateActiveStatus(QRCode entity, List<ErrorValidation> validations) {
        qrCodeQueryService.chekValidateQRCode(entity, validations, "qr_code");
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
        if (entity.isActive() && entity.getType().equals(QRCodeType.SINGLE_TRX_USE)) {
            entity.setStatus(QRCodeStatus.INACTIVE);
        }
        return repository.save(entity);
    }
    public QRCode activateQrCode(QRCode entity) {
        if (QRCodeStatus.PENDING.equals(entity.getStatus())) {
            List<ErrorValidation> validations = new ArrayList<>();
            validateActiveStatus(entity, validations);
            entity.setStatus(QRCodeStatus.ACTIVE);
            BadRequestException.ThrowWhenError("Activate QR error", validations,null);
        }else {
            throw new BadRequestException("QR Code is not in pending status");
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
        customerCrewQueryService.relation(dto.getUser(), validations, "customerCrew").ifPresent(entity::setUser);
        customerQueryService.relation(dto.getCustomer(), validations, "customer").ifPresent(entity::setCustomer);
        if (QRCodeStatus.ACTIVE.equals(entity.getStatus())) {
            repairCode(entity);
            validateActiveStatus(entity, validations);
        }
        BadRequestException.ThrowWhenError("Validation error", validations,dto);
        return entity;
    }

    @Override
    public QRCode convertFromUpdateDTO(String id, QRCodeUpdateDTO dto) {
        QRCode entity = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
        if (!QRCodeStatus.PENDING.equals(entity.getStatus())) {
            throw new BadRequestException("QR Code is not in pending status. Cannot be updated");
        }
        mapper.updateEntityFromUpdateDto(dto, entity);
        val validations = new ArrayList<ErrorValidation>();
        branchQueryService.relation(dto.getBranch(), validations, "branch").ifPresent(entity::setBranch);
        serviceProductQueryService.relation(dto.getServiceProduct(), validations, "service_product").ifPresent(entity::setServiceProduct);
        beneficiaryAccountQueryService.relation(dto.getBeneficiaryAccount(), validations, "beneficiary_account").ifPresent(entity::setBeneficiaryAccount);
        customerCrewQueryService.relation(dto.getUser(), validations, "user").ifPresent(entity::setUser);
        customerQueryService.relation(dto.getCustomer(), validations, "customer").ifPresent(entity::setCustomer);
        if (QRCodeStatus.ACTIVE.equals(entity.getStatus())) {
            repairCode(entity);
            validateActiveStatus(entity, validations);
        }
        BadRequestException.ThrowWhenError("Validation error", validations,dto);
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
    public QRCodeQueryDTO repair(UUID id) {

        val entity= repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
        if (repairCode(entity)){
            publishEntityEvent(EventType.UPDATE, entity);
        }
        return mapper.entityToQueryDto(entity);
    }
}
