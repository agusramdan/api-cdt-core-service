package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeCommandService;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

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

    public TrxDepositQueryDTO createTrxDeposit(TrxDepositCreateDTO dto) {
        val option_trx = repository.findByTokenAndSignature(dto.getToken(), dto.getSignature());
        if (option_trx.isPresent()){
            log.info("Resend detected Token and Signature and Amount");
        }
        return option_trx.or(() -> {
            val token = new QRCodeDTO(dto.getToken());
            val validations =  new ArrayList<ErrorValidation>();
            val code = codeQueryService.getForRelation(token,validations,"qr_code");
            val machine = machineQueryService.getForRelation(dto.getMachine(),validations,"machine");
            // save deposit
            if (validations.isEmpty() && code!=null && !code.isActive()) {
                validations.add(ErrorValidation.New("QR Code not active!","qr_code",dto.getToken()));
            }
            if (validations.isEmpty()){
                if (machine==null) {
                    validations.add(ErrorValidation.New("Machine/Terminal not register!","machine",dto.getMachine().getCode()));
                }
                val product = code.getServiceProduct();
                if (product==null){
                    validations.add(ErrorValidation.New("Invalid Product qr code","qr_code",dto.getToken()));
                } else if (!"MUL-ST-TR".equals(product.getCode())) {
                    validations.add(ErrorValidation.New("Invalid Product qr code","qr_code",dto.getToken()));
                }
                val beneficiaryAccount = code.getBeneficiaryAccount();
                if (beneficiaryAccount==null){
                    validations.add(ErrorValidation.New("Invalid BeneficiaryAccount qr code","qr_code",dto.getToken()));
                } else {
                    if(beneficiaryAccount.getBank()==null){
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.Bank qr code","qr_code",dto.getToken()));
                    }
                    if(beneficiaryAccount.getCountryCode()==null){
                        validations.add(ErrorValidation.New("Invalid BeneficiaryAccount.CountryCode qr code","qr_code",dto.getToken()));
                    }
                }
            }
            if (validations.size()>0){
                throw new BadRequestException("Invalid Transaction",validations);
            }
            var deposit = trxDepositMapper.toEntity(dto);
            deposit.setMachine(machine);
            deposit.setCode(code);
            deposit.setUser(code.getUser());
            deposit.setServiceProduct(code.getServiceProduct());
            deposit.setBeneficiaryAccount(code.getBeneficiaryAccount());
            deposit.setStatus(TrxDepositStatus.DEPOSIT);
            log.info("Deposit; id={}; amount={}; product={};",deposit.getId(),deposit.getAmount(),deposit.getServiceProduct().getCode());
            deposit = repository.save(deposit);
            codeCommandService.useCode(deposit.getCode());
            return Optional.of(deposit);
        }).map(this::prepareTransaction
        ).map(this::executeTransaction
        ).map(trxDepositMapper::entityToQueryDto
        ).orElse(null);
    }

    protected TrxDeposit prepareTransaction(TrxDeposit deposit){
        if(TrxDepositStatus.DEPOSIT.equals(deposit.getStatus())){
            var trx = transactionService.prepare(deposit);
            deposit.setServiceTransaction(trx);
            deposit.setStatus(TrxDepositStatus.TRANSFER_IN_PROGRESS);
            deposit = repository.save(deposit);
        }
        return deposit;
    }

    protected TrxDeposit executeTransaction(TrxDeposit deposit){
        if (TrxDepositStatus.TRANSFER_IN_PROGRESS.equals(deposit.getStatus())) {
            val trx = transactionService.executeTransaction(deposit.getServiceTransaction());
            switch (trx.getStatus()) {
                case SUCCESS, TRANSFER_SUCCESS:
                    deposit.setStatus(TrxDepositStatus.SUCCESS);
                    break;
            }
            deposit = repository.save(deposit);
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
        repository.deleteById(id);
    }
}

