package agus.ramdan.cdt.core.trx.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.pickup.TrxPickupUpdateDTO;
import agus.ramdan.cdt.core.trx.service.pickup.TrxPickupCommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/pickup/command")
@RequiredArgsConstructor
@Getter
public class TrxPickupCommandController implements
        BaseCommandController<TrxPickupQueryDTO, TrxPickupCreateDTO, TrxPickupUpdateDTO, String> {

    private final TrxPickupCommandService service;

}

