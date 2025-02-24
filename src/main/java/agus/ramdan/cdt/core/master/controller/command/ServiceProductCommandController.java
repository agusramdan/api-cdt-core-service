package agus.ramdan.cdt.core.master.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.product.ServiceProductUpdateDTO;
import agus.ramdan.cdt.core.master.service.product.ServiceProductCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/service-product/command")
@Tag(name = "Service Product Command API", description = "APIs for creating, updating, and deleting service product")
@RequiredArgsConstructor
@Getter
public class ServiceProductCommandController implements BaseCommandController<ServiceProductQueryDTO, ServiceProductCreateDTO, ServiceProductUpdateDTO, String> {
    private final ServiceProductCommandService service;
}