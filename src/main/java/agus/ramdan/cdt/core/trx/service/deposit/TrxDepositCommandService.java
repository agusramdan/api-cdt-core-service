package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.base.dto.EventType;
import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.PropagationXxxException;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.controller.dto.ServiceRuleConfig;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import agus.ramdan.cdt.core.trx.service.TrxDataEventProducerService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeCommandService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionService;
import agus.ramdan.cdt.core.utils.EntityFallbackFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
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
            if (validations.isEmpty() && !code.isActive()) {
                validations.add(ErrorValidation.New("QR Code not active!", "qr_code", dto.getToken()));
            }
            if (validations.isEmpty()) {
//                val customer = EntityFallbackFactory.ensureNotLazy(validations, "Invalid QR Code.Customer", "qr_code",()->code.getCustomer());
//                if (customer == null) {
//                    validations.add(ErrorValidation.New("Invalid QR Code.Customer ", "qr_code", dto.getToken()));
//                }
                val user = EntityFallbackFactory.ensureNotLazy(validations, "Invalid User", "qr_code",()->code.getUser());
                if (user == null) {
                    validations.add(ErrorValidation.New("Invalid QRCode.User", "qr_code", dto.getToken()));
                }else {
                    if (!user.getUsername().equals(dto.getUsername())){
                        validations.add(ErrorValidation.New("Invalid QRCode.Username", "qr_code", dto.getToken()));
                    }
                }
                if (machine == null) {
                    validations.add(ErrorValidation.New("Machine/Terminal not register!", "machine", dto.getMachine().getCode()));
                }
                val product = EntityFallbackFactory.ensureNotLazy(validations, "Invalid Product", "qr_code", code::getServiceProduct);
                if (product == null) {
                    validations.add(ErrorValidation.New("Invalid qr code Product", "qr_code", dto.getToken()));
                }else {
                    if(!ServiceRuleConfig.DEPOSIT.equals(product.getServiceRuleConfig())) {
                        validations.add(ErrorValidation.New("Invalid qr code Product Service", "qr_code", dto.getToken()));
                    }
                }
                val beneficiaryAccount = EntityFallbackFactory.ensureNotLazy(validations, "Invalid BeneficiaryAccount", "qr_code", code::getBeneficiaryAccount);
                if (beneficiaryAccount == null) {
                    validations.add(ErrorValidation.New("Invalid BeneficiaryAccount", "qr_code", dto.getToken()));
                } else {
                    val customerBen = EntityFallbackFactory.ensureNotLazy(validations, "Invalid BeneficiaryAccount.Customer", "qr_code",()->beneficiaryAccount.getCustomer());
                    if (customerBen == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Customer ", "qr_code", dto.getToken()));
                    }
                    val brance = EntityFallbackFactory.ensureNotLazy(validations, "Invalid BeneficiaryAccount.Branch", "qr_code", beneficiaryAccount::getBranch);
                    if (brance == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Branch ", "qr_code", dto.getToken()));
                    }
                    if (beneficiaryAccount.getBank() == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Bank", "qr_code", dto.getToken()));
                    }
                    if (beneficiaryAccount.getCountryCode() == null) {
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.CountryCode", "qr_code", dto.getToken()));
                    }
//                    if (validations.isEmpty() && !customerBen.getId().equals(customer.getId())) {
//                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Customer and User.Customer", "qr_code", dto.getToken()));
//                    }
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
//    protected TrxDeposit prepareTransaction(TrxDeposit deposit) {
//        if (TrxDepositStatus.DEPOSIT.equals(deposit.getStatus())) {
//            var trx = transactionService.prepare(deposit);
//            deposit.setServiceTransaction(trx);
//            deposit.setStatus(TrxDepositStatus.TRANSFER_IN_PROGRESS);
//            deposit.setPjpurStatus(TrxDepositPjpurStatus.PREPARE);
//            deposit = repository.save(deposit);
//            trxDataEventProducerService.publishDataEvent(EventType.CREATE,deposit);
//        }
//        return deposit;
//    }

//    protected TrxDeposit pjpurNotification(TrxDeposit deposit) {
//        if (Arrays.asList(TrxDepositStatus.PJPUR,TrxDepositStatus.PJPUR_FAILED,TrxDepositStatus.PJPUR_TIMEOUT).contains(deposit.getStatus())
//                || Arrays.asList(TrxDepositPjpurStatus.FAIL, TrxDepositPjpurStatus.TIMEOUT).contains(deposit.getPjpurStatus())
//                || deposit.getPjpurStatus() == null
//        ) {
//            deposit.setStatus(TrxDepositStatus.PJPUR_IN_PROGRESS);
//            deposit = repository.save(deposit);
//            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
//        }
//        if (TrxDepositStatus.PJPUR_IN_PROGRESS.equals(deposit.getStatus()) ) {
//            try {
//                deposit = pjpurService.deposit(deposit);
//                deposit.setStatus(switch (deposit.getPjpurStatus()) {
//                    case SUCCESS -> TrxDepositStatus.PJPUR_SUCCESS;
//                    case FAIL -> TrxDepositStatus.PJPUR_FAILED;
//                    case TIMEOUT -> TrxDepositStatus.PJPUR_TIMEOUT;
//                    default -> TrxDepositStatus.PJPUR;
//                });
//            } catch (Propagation4xxException e) {
//                log.warn("PJPUR Notification",e);
//                deposit.setStatus(TrxDepositStatus.PJPUR_FAILED);
//            } catch (Propagation5xxException e) {
//                deposit.setStatus(TrxDepositStatus.PJPUR_TIMEOUT);
//            }
//            deposit = repository.save(deposit);
//            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
//        }else {
//            log.warn("PJPUR Notification; id={}; status={}; psjpu_status={};", deposit.getId(),deposit.getStatus(), deposit.getPjpurStatus());
//        }
//        return deposit;
//    }

    protected TrxDepositQueryDTO executeTransaction(TrxDeposit deposit) {
//        if(TrxDepositStatus.SUCCESS.equals(deposit.getStatus()) ) {
//            return deposit;
//        }
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
//
//        if (pjpurConfig.isTrxBlocking()){
//            if (TrxDepositStatus.PJPUR_SUCCESS.equals(deposit.getStatus()) ) {
//                deposit.setStatus(TrxDepositStatus.TRANSFER);
//            }
//        }else if(Arrays.asList(TrxDepositStatus.PJPUR,TrxDepositStatus.PJPUR_FAILED,TrxDepositStatus.PJPUR_TIMEOUT,TrxDepositStatus.PJPUR_IN_PROGRESS).contains(deposit.getStatus())) {
//            deposit.setStatus(TrxDepositStatus.TRANSFER);
//        }
//        if (Arrays.asList(TrxDepositStatus.TRANSFER,TrxDepositStatus.TRANSFER_GATEWAY_TIMEOUT).contains(deposit.getStatus())) {
//            deposit.setStatus(TrxDepositStatus.TRANSFER_IN_PROGRESS);
//            deposit = repository.save(deposit);
//            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
//        }
//        if (TrxDepositStatus.TRANSFER_IN_PROGRESS.equals(deposit.getStatus())) {
//            try {
//                val trx = transactionService.executeTransaction(deposit.getServiceTransaction());
//                switch (trx.getStatus()) {
//                    case SUCCESS, TRANSFER_SUCCESS:
//                        deposit.setStatus(TrxDepositStatus.SUCCESS);
//                        break;
//                }
//            } catch (Propagation5xxException e) {
//                deposit.setStatus(TrxDepositStatus.TRANSFER_GATEWAY_TIMEOUT);
//            }
//            deposit = repository.save(deposit);
//            trxDataEventProducerService.publishDataEvent(EventType.UPDATE,deposit);
//        }
//        return deposit;
    }

//    public TrxDepositQueryDTO updateTrxDeposit(TrxDepositUpdateDTO dto) {
//        TrxDeposit trxDeposit = repository.findById(dto.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
//        trxDeposit.setStatus(TrxDepositStatus.valueOf(dto.getStatus()));
//        return trxDepositMapper.entityToQueryDto(repository.save(trxDeposit));
//    }

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

