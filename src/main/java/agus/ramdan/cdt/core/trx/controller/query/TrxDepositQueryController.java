package agus.ramdan.cdt.core.trx.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.service.deposit.TrxDepositQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/deposit/query")
@Tag(name = "Cash Deposit API", description = "APIs for query Cash Deposit")
@RequiredArgsConstructor
@Log4j2
@Getter
public class TrxDepositQueryController implements BaseQueryController<TrxDepositQueryDTO, String> {
    private final TrxDepositQueryService service;

//    @GetMapping
//    public ResponseEntity<List<TrxDepositQueryDTO>> getAll() {
//        return ResponseEntity.ok(service.getAllTrxDeposits());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TrxDepositQueryDTO> getById(@PathVariable UUID id) {
//        return ResponseEntity.ok(service.getTrxDepositById(id));
//    }
}
