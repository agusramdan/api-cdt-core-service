package agus.ramdan.cdt.core.trx.controller.command;

import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.TrxDepositMapper;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionQueryService;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/service-transaction/command")
@Tag(name = "Service Transaction Command API", description = "APIs for resend, Transfer to Payment Gateway")
@RequiredArgsConstructor
@Log4j2
public class ServiceTransactionCommandController {
    private final ServiceTransactionService transactionService;
    private final ServiceTransactionQueryService queryService;
    @PostMapping("/all/prepare")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
    })
    public void prepareAll() {
        queryService.getRepository().findAllByServiceProductIsNull(Pageable.ofSize(100)).forEach(
                trx -> {
                    transactionService.prepare(trx);
                }
        );
    }
}
