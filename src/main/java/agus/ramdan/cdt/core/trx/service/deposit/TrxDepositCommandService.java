package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.base.dto.DepositCheckStatusDTO;
import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.PropagationXxxException;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.utils.EntityFallbackFactory;
import agus.ramdan.cdt.core.master.controller.dto.ServiceRuleConfig;
import agus.ramdan.cdt.core.master.persistence.domain.Machine;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeCommandService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional(noRollbackFor = PropagationXxxException.class)
    public TrxDepositQueryDTO createTrxDeposit(TrxDepositCreateDTO dto) {
        val token = dto.getToken();
        val qrCodeDTO = new QRCodeDTO(dto.getToken());
        val validations = new ArrayList<ErrorValidation>();
        val code = codeQueryService.getForRelation(qrCodeDTO, validations, "qr_code");
        val machine = machineQueryService.getForRelation(dto.getMachine(), validations, "machine");
        val option_trx = repository.findByTokenAndCdmTrxNoAndCdmTrxDateAndMachine(token, dto.getCdmTrxNo(), dto.getCdmTrxDate(), machine);
        option_trx.ifPresentOrElse(depo -> {
            if(!TrxDepositStatus.CANCELLED.equals(depo.getStatus())) {
                throw new BadRequestException("Duplicate Deposit");
            }
        }, () -> {
            if (code == null) {
                validations.add(ErrorValidation.New("QR Code not found!", "qr_code", dto.getToken()));
            } else {
                codeQueryService.chekValidateQRCode(code, validations, "qr_code");
            }
            if (validations.isEmpty() && !code.isActive()) {
                validations.add(ErrorValidation.New("QR Code not active!", "qr_code", dto.getToken()));
            }
            if (validations.isEmpty()) {
                val user = EntityFallbackFactory.ensureNotLazy(validations, "Invalid User", "qr_code", code::getUser);
                if (user == null) {
                    validations.add(ErrorValidation.New("Invalid QRCode.User", "qr_code", dto.getToken()));
                }else {
                    if (!user.getUsername().equals(dto.getUsername())){
                        validations.add(ErrorValidation.New("Invalid QRCode.Username", "qr_code", dto.getToken()));
                    }
                }
                if (machine == null) {
                    validations.add(ErrorValidation.New("Machine/Terminal not register!", "machine", dto.getMachine().getCode()));
                }else {
                    Machine machineCode = EntityFallbackFactory.ensureNotLazy(validations, "Invalid Machine", "qr_code", code::getMachine);
                    if (machineCode != null && !machine.getCode().equals(machineCode.getCode())){
                        validations.add(ErrorValidation.New("Invalid QRCode.Machine cannot use in this machine", "qr_code", machine.getCode()));
                    }
                }
                val product = EntityFallbackFactory.ensureNotLazy(validations, "Invalid Product", "qr_code", code::getServiceProduct);
                if (product == null) {
                    validations.add(ErrorValidation.New("Invalid qr code Product", "qr_code", dto.getToken()));
                }else {
                    if(!ServiceRuleConfig.DEPOSIT.equals(product.getServiceRuleConfig())) {
                        validations.add(ErrorValidation.New("Invalid qr code Product Service", "qr_code", dto.getToken()));
                    }
                }
            }

            BadRequestException.ThrowWhenError("Invalid Parameter Transaction", validations,dto);
        });

        return option_trx.or(() -> Optional.of(trxDepositMapper.toEntity(dto, machine, code))
                        .stream()
                        .peek(deposit -> deposit.setStatus(TrxDepositStatus.DEPOSIT))
                        .peek(deposit -> log.info("Deposit; id={}; amount={}; product={};", deposit.getId(), deposit.getAmount(), deposit.getServiceProduct().getCode()))
                        .map(repository::saveAndFlush)
                        .peek(deposit -> codeCommandService.useCode(deposit.getCode()))
                        .findFirst())
                .map(this::executeTransaction)
                .orElse(null);
    }

    public TrxDepositQueryDTO resend(UUID id) {
        return repository.findById(id)
                .map(this::executeTransaction)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    protected TrxDepositQueryDTO executeTransaction(TrxDeposit deposit) {
        try {
            ServiceTransaction trx = deposit.getServiceTransaction();
            if (trx == null) {
                trx = transactionService.prepare(deposit);
                deposit.setServiceTransaction(trx);
            }
            trx = transactionService.prepare(trx);
            trx = transactionService.transaction(trx);
            deposit.setStatus(
                    switch (trx.getStatus()) {
                        case SUCCESS,TRANSFER_SUCCESS -> TrxDepositStatus.SUCCESS;
                        case FAILED -> TrxDepositStatus.FAILED;
                        default -> TrxDepositStatus.TRANSFER_IN_PROGRESS;
                    }
            );
            deposit = repository.save(deposit);
        }finally {
            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
        }
        return trxDepositMapper.entityToQueryDto(deposit);
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

    public TrxDepositQueryDTO checkStatus(UUID id) {
        val trxDeposit = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return trxDepositMapper.entityToQueryDto(checkStatus(trxDeposit));
    }

    public TrxDeposit checkStatus(@NotNull TrxDeposit deposit) {
        if(!TrxDepositStatus.SUCCESS.equals(deposit.getStatus())) {
            val sc = deposit.getServiceTransaction();
            if (TrxStatus.SUCCESS.equals(sc.getStatus())) {
                deposit.setStatus(TrxDepositStatus.SUCCESS);
                repository.save(deposit);
                trxDataEventProducerService.publishDataEvent(EventType.UPDATE, deposit);
            }
        }
        return deposit;
    }

    @KafkaListener(topics = "core-deposit-status-check-event", groupId = "cdt-core-transaction-callback")
    @Transactional(noRollbackFor = PropagationXxxException.class)
    public void depositCallback(DepositCheckStatusDTO trxNo) {
        val trx = repository.findById(trxNo.getId());
        if (trx.isPresent()) {
            checkStatus(trx.get());
        } else {
            log.error("Transaction not found; id={}", trxNo.getId());
        }
    }
}
