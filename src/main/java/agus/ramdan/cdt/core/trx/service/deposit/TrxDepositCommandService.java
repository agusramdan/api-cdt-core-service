package agus.ramdan.cdt.core.trx.service.deposit;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.cdt.core.master.service.machine.MachineQueryService;
import agus.ramdan.cdt.core.trx.controller.dto.QRCodeDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositUpdateDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDeposit;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxDepositStatus;
import agus.ramdan.cdt.core.trx.persistence.repository.TrxDepositRepository;
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
    private final MachineQueryService machineQueryService;
    private final ServiceTransactionService transactionService;

    public TrxDepositQueryDTO createTrxDeposit(TrxDepositCreateDTO dto) {
        val option_trx = repository.findByTokenAndSignatureAndAmount(dto.getToken(), dto.getSignature(),dto.getAmount());
        if (option_trx.isPresent()){
            log.info("Resend detected Token and Signature and Amount");
        }
        return option_trx.or(() -> {
            var deposit = trxDepositMapper.toEntity(dto);
            val token = new QRCodeDTO(dto.getToken());
            val validations =  new ArrayList<ErrorValidation>();
            val code = codeQueryService.getForRelation(token,validations,"qr_code");
            val machine = machineQueryService.getForRelation(dto.getMachine(),validations,"machine");
            if (validations.size()>0){
                throw new BadRequestException("Invalid Transaction",validations.toArray(new ErrorValidation[0]));
            }
            // save deposit
            deposit.setCode(code);
            deposit.setServiceProduct(code.getServiceProduct());
            deposit.setMachine(machine);
            deposit.setStatus(TrxDepositStatus.DEPOSIT);
            deposit = repository.save(deposit);

            // prepare transfer
            deposit = prepareTransfer(deposit);

            // Transfer Fund
            deposit = transferFund(deposit);

            return Optional.of(deposit);
        }).map(trxDepositMapper::entityToQueryDto).orElse(null);
    }

    public TrxDeposit prepareTransfer(TrxDeposit deposit){
        ServiceTransaction trx = transactionService.prepare(deposit);
        deposit.setServiceTransaction(trx);
        deposit.setStatus(TrxDepositStatus.TRANSFER_IN_PROGRESS);
        deposit = repository.save(deposit);
        return deposit;
    }

    public TrxDeposit transferFund(TrxDeposit deposit){
        transactionService.transferFund(deposit.getServiceTransaction());
        deposit.setStatus(TrxDepositStatus.SUCCESS);
        deposit = repository.save(deposit);
        return deposit;
    }

    public TrxDepositQueryDTO updateTrxDeposit(TrxDepositUpdateDTO dto) {
        TrxDeposit trxDeposit = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        trxDeposit.setStatus(TrxDepositStatus.valueOf(dto.getStatus()));
        return trxDepositMapper.entityToQueryDto(repository.save(trxDeposit));
    }

    public void deleteTrxDeposit(UUID id) {
        repository.deleteById(id);
    }
}

