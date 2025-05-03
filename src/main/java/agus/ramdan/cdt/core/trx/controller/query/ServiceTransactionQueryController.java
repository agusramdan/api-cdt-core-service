package agus.ramdan.cdt.core.trx.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.transaction.ServiceTransactionQueryDTO;
import agus.ramdan.cdt.core.trx.persistence.domain.ServiceTransaction;
import agus.ramdan.cdt.core.trx.service.pickup.TrxPickupQueryService;
import agus.ramdan.cdt.core.trx.service.transaction.ServiceTransactionQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/service-transaction/query")
@Tag(name = "Service Transaction Query API", description = "APIs for query Pickup")
@RequiredArgsConstructor
@Log4j2
@Getter
public class ServiceTransactionQueryController implements BaseQueryController<ServiceTransactionQueryDTO, String> {
    private final ServiceTransactionQueryService service;
}
