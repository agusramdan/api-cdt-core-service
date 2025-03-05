package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.vendorcrew.VendorCrewQueryDTO;
import agus.ramdan.cdt.core.master.service.vendorcrew.VendorCrewQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/vendor-crew/query")
@Tag(name = "Vendor Crew Query API", description = "APIs for query VendorCrew")
@RequiredArgsConstructor
@Getter
public class VendorCrewQueryController implements
        BaseQueryController<VendorCrewQueryDTO, String> {
    private final VendorCrewQueryService service;
    @GetMapping("/username/{username}")
    public ResponseEntity<VendorCrewQueryDTO> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getByUsername(username));
    }
}
