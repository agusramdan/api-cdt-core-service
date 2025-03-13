package agus.ramdan.cdt.core.trx.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.service.deposit.TrxDepositQueryService;
import agus.ramdan.cdt.core.trx.service.pickup.TrxPickupQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/pickup/query")
@Tag(name = "Pickup Query API", description = "APIs for query Pickup")
@RequiredArgsConstructor
@Log4j2
@Getter
public class TrxPickupQueryController implements BaseQueryController<TrxPickupQueryDTO, String> {
    private final TrxPickupQueryService service;
}
