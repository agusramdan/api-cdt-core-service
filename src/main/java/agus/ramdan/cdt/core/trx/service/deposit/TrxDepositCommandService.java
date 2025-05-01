package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.*;
import agus.ramdan.cdt.core.config.PjpurConfig;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositPjpurStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.pjpur.PjpurService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeCommandService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionService;
import agus.ramdan.cdt.core.utils.EntityFallbackFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrxDepositCommandService {
    private final TrxDepositRepository repository;
    private final TrxDepositMapper trxDepositMapper;
    private final QRCodeQueryService codeQueryService;
    private final QRCodeCommandService codeCommandService;
    private final MachineQueryService machineQueryService;
    private final ServiceTransactionService transactionService;
    private final TrxDataEventProducerService trxDataEventProducerService;
    private final PjpurService pjpurService;
    private final PjpurConfig pjpurConfig;

    public TrxDepositQueryDTO createTrxDeposit(TrxDepositCreateDTO dto) {
        val token = dto.getToken();
        val qrCodeDTO = new QRCodeDTO(dto.getToken());
        val validations = new ArrayList<ErrorValidation>();
        val code = codeQueryService.getForRelation(qrCodeDTO, validations, "qr_code");
        val machine = machineQueryService.getForRelation(dto.getMachine(), validations, "machine");
        val option_trx = repository.findByTokenAndCdmTrxNoAndCdmTrxDateAndMachine(token, dto.getCdmTrxNo(), dto.getCdmTrxDate(), machine);
        option_trx.ifPresentOrElse(depo -> {
            if (TrxDepositStatus.TRANSFER_GATEWAY_TIMEOUT != depo.getStatus()) {
                throw new BadRequestException("Duplicate Deposit");
            }
        }, () -> {
            if (validations.isEmpty() && code != null && !code.isActive()) {
                validations.add(ErrorValidation.New("QR Code not active!", "qr_code", dto.getToken()));
            }
            if (validations.isEmpty()) {
                if (machine == null) {
                    validations.add(ErrorValidation.New("Machine/Terminal not register!", "machine", dto.getMachine().getCode()));
                }
                val product = EntityFallbackFactory.ensureNotLazy(validations, "Invalid Product", "qr_code",code::getServiceProduct);
                if (product == null || !"MUL-ST-TR".equals(product.getCode())) {
                    validations.add(ErrorValidation.New("Invalid qr code Product", "qr_code", dto.getToken()));
                }
                val beneficiaryAccount = EntityFallbackFactory.ensureNotLazy(validations, "Invalid BeneficiaryAccount", "qr_code",code::getBeneficiaryAccount);
                if (beneficiaryAccount == null) {
                    validations.add(ErrorValidation.New("Invalid BeneficiaryAccount", "qr_code", dto.getToken()));
                } else {
                    val brance = EntityFallbackFactory.ensureNotLazy(validations, "Invalid BeneficiaryAccount.Branch", "qr_code",beneficiaryAccount::getBranch);
                    if (brance == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Branch ", "qr_code", dto.getToken()));
                    }
                    if (beneficiaryAccount.getBank() == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Bank", "qr_code", dto.getToken()));
                    }
                    if (beneficiaryAccount.getCountryCode() == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.CountryCode", "qr_code", dto.getToken()));
                    }
                }
            }
            BadRequestException.ThrowWhenError("Invalid Parameter Transaction", validations,dto);
        });

        return option_trx.or(() -> Optional.of(trxDepositMapper.toEntity(dto, machine, code))
                        .stream()
                        .peek(deposit -> deposit.setStatus(TrxDepositStatus.DEPOSIT))
                        .peek(deposit -> log.info("Deposit; id={}; amount={}; product={};", deposit.getId(), deposit.getAmount(), deposit.getServiceProduct().getCode()))
                        .map(repository::save)
                        .peek(deposit -> codeCommandService.useCode(deposit.getCode()))
                        .findFirst())
                .map(this::prepareTransaction)
                .map(this::pjpurNotification)
                .map(this::executeTransaction)
                .map(trxDepositMapper::entityToQueryDto)
                .orElse(null);
    }
    public TrxDepositQueryDTO resend(UUID id) {
        val option_trx = repository.findById(id);
        option_trx.orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return option_trx
                .map(this::pjpurNotification)
                .map(this::executeTransaction)
                .map(trxDepositMapper::entityToQueryDto)
                .orElse(null);
    }
    protected TrxDeposit prepareTransaction(TrxDeposit deposit) {
        if (TrxDepositStatus.DEPOSIT.equals(deposit.getStatus())) {
            var trx = transactionService.prepare(deposit);
            deposit.setServiceTransaction(trx);
            deposit.setStatus(TrxDepositStatus.TRANSFER_IN_PROGRESS);
            deposit.setPjpurStatus(TrxDepositPjpurStatus.PREPARE);
            deposit = repository.save(deposit);
            trxDataEventProducerService.publishDataEvent(EventType.CREATE,deposit);
        }
        return deposit;
    }

    protected TrxDeposit pjpurNotification(TrxDeposit deposit) {
        if (Arrays.asList(TrxDepositStatus.PJPUR,TrxDepositStatus.PJPUR_FAILED,TrxDepositStatus.PJPUR_TIMEOUT).contains(deposit.getStatus())
                || Arrays.asList(TrxDepositPjpurStatus.FAIL, TrxDepositPjpurStatus.TIMEOUT).contains(deposit.getPjpurStatus())
                || deposit.getPjpurStatus() == null
        ) {
            deposit.setStatus(TrxDepositStatus.PJPUR_IN_PROGRESS);
            deposit = repository.save(deposit);
            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
        }
        if (TrxDepositStatus.PJPUR_IN_PROGRESS.equals(deposit.getStatus()) ) {
            try {
                deposit = pjpurService.deposit(deposit);
                deposit.setStatus(switch (deposit.getPjpurStatus()) {
                    case SUCCESS -> TrxDepositStatus.PJPUR_SUCCESS;
                    case FAIL -> TrxDepositStatus.PJPUR_FAILED;
                    case TIMEOUT -> TrxDepositStatus.PJPUR_TIMEOUT;
                    default -> TrxDepositStatus.PJPUR;
                });
            } catch (Propagation4xxException e) {
                log.warn("PJPUR Notification",e);
                deposit.setStatus(TrxDepositStatus.PJPUR_FAILED);
            } catch (Propagation5xxException e) {
                deposit.setStatus(TrxDepositStatus.PJPUR_TIMEOUT);
            }
            deposit = repository.save(deposit);
            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
        }else {
            log.warn("PJPUR Notification; id={}; status={}; psjpu_status={};", deposit.getId(),deposit.getStatus(), deposit.getPjpurStatus());
        }
        return deposit;
    }

    protected TrxDeposit executeTransaction(TrxDeposit deposit) {
        if (pjpurConfig.isTrxBlocking()){
            if (TrxDepositStatus.PJPUR_SUCCESS.equals(deposit.getStatus()) ) {
                deposit.setStatus(TrxDepositStatus.TRANSFER);
            }
        }else if(Arrays.asList(TrxDepositStatus.PJPUR,TrxDepositStatus.PJPUR_FAILED,TrxDepositStatus.PJPUR_TIMEOUT,TrxDepositStatus.PJPUR_IN_PROGRESS).contains(deposit.getStatus())) {
            deposit.setStatus(TrxDepositStatus.TRANSFER);
        }
        if (Arrays.asList(TrxDepositStatus.TRANSFER,TrxDepositStatus.TRANSFER_GATEWAY_TIMEOUT).contains(deposit.getStatus())) {
            deposit.setStatus(TrxDepositStatus.TRANSFER_IN_PROGRESS);
            deposit = repository.save(deposit);
            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
        }
        if (TrxDepositStatus.TRANSFER_IN_PROGRESS.equals(deposit.getStatus())) {
            try {
                val trx = transactionService.executeTransaction(deposit.getServiceTransaction());
                switch (trx.getStatus()) {
                    case SUCCESS, TRANSFER_SUCCESS:
                        deposit.setStatus(TrxDepositStatus.SUCCESS);
                        break;
                }
            } catch (Propagation5xxException e) {
                deposit.setStatus(TrxDepositStatus.TRANSFER_GATEWAY_TIMEOUT);
            }
            deposit = repository.save(deposit);
            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
        }
        return deposit;
    }

    public TrxDepositQueryDTO updateTrxDeposit(TrxDepositUpdateDTO dto) {
        TrxDeposit trxDeposit = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        trxDeposit.setStatus(TrxDepositStatus.valueOf(dto.getStatus()));
        return trxDepositMapper.entityToQueryDto(repository.save(trxDeposit));
    }

    public void deleteTrxDeposit(UUID id) {
        val trxDeposit = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (trxDeposit.getStatus() == TrxDepositStatus.CANCELLED) {
            repository.delete(trxDeposit);
            trxDataEventProducerService.publishDataEvent(EventType.DELETE,trxDeposit);
        }
        throw new BadRequestException("Transaction already processed");
    }
}

