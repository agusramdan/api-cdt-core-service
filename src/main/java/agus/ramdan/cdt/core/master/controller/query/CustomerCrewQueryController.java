package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.customercrew.CustomerCrewQueryDTO;
import agus.ramdan.cdt.core.master.service.customercrew.CustomerCrewQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/customer-crew/query")
@Tag(name = "Customer Crew Query API", description = "APIs for query CustomerCrew")
@RequiredArgsConstructor
@Getter
public class CustomerCrewQueryController implements
        BaseQueryController<CustomerCrewQueryDTO, String> {
    private final CustomerCrewQueryService service;
    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerCrewQueryDTO> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getByUsername(username));
    }
}
