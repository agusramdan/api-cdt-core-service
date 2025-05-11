package agus.ramdan.cdt.core.trx.controller.command;

import agus.ramdan.base.dto.TransactionCheckStatusDTO;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.transaction.ServiceTransactionQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.TrxStatus;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cdt/core/trx/service-transaction/command")
@Tag(name = "Service Transaction Command API", description = "APIs for resend, Transfer to Payment Gateway")
@RequiredArgsConstructor
@Log4j2
public class ServiceTransactionCommandController {
    private final ServiceTransactionQueryService queryService;
    private final KafkaTemplate<String, TransactionCheckStatusDTO> kafkaTemplate;
    private final ServiceTransactionService service;

    @PostMapping("/all/retry")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
    })
    public void retryAll() {
        int count = 0;
        Pageable page = PageRequest.of(0,100, Sort.Direction.ASC,"no");
        while (true) {
            val transactions = queryService.getRepository().findByStatusNot(TrxStatus.SUCCESS, page);
            if (transactions.isEmpty()) {
                log.info("Execute {}",count);
                break;
            }
            count +=transactions.size();
            transactions.forEach((t) ->
                kafkaTemplate.send("core-trx-status-check-event",TransactionCheckStatusDTO.builder()
                        .id(t.getId())
                        .source("ServiceTransactionCommandController")
                        .message("Retry Transaction : "+t.getStatus())
                        .build())
            );
            page = page.next();
        }

    }

    @PostMapping("/all/retry/{status}")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
    })
    public void retryAllStatus(@PathVariable String status) {
        int count = 0;
        Pageable page = PageRequest.of(0,100, Sort.Direction.ASC,"no");
        while (true) {
            val transactions = queryService.getRepository().findByStatus(TrxStatus.valueOf(status), page);
            if (transactions.isEmpty()) {
                log.info("Execute {}",count);
                break;
            }
            count +=transactions.size();
            transactions.forEach((t) ->
                kafkaTemplate.send("core-trx-status-check-event",
                        TransactionCheckStatusDTO.builder()
                                .id(t.getId())
                                .source("ServiceTransactionCommandController")
                                .message("Retry Transaction :"+status)
                                .build())
            );
            page = page.next();
        }

    }

    @PutMapping("/{id}/check_status")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceTransactionQueryDTO.class)),})
    })
    public ResponseEntity<ServiceTransactionQueryDTO> checkStatus(@PathVariable UUID id) {
        val result = service.checkStatus(id);
        if (!"SUCCESS".equals(result.getStatus())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
