package agus.ramdan.cdt.core.trx.controller.command;

import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/transfer/command")
@Tag(name = "Cash Transfer Command API", description = "APIs for resend, Transfer to Payment Gateway")
@RequiredArgsConstructor
@Log4j2
public class TrxTransferCommandController {
    private final ServiceTransactionService transactionService;
    private final TrxDepositMapper trxDepositMapper;

//    @PostMapping("/{trxNo}/retry")
//    @ApiResponses(value = {
//            @ApiResponse(description = "successful operation", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
//    })
//    public ResponseEntity<TrxDepositQueryDTO> retry(@PathVariable String trxNo) {
//        val result = trxDepositMapper.entityToQueryDto(transactionService.retryTransfer(trxNo).getDeposit());
//        if ("TRANSFER_GATEWAY_TIMEOUT".equals(result.getStatus())) {
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
//        }
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
//    }
}
