package agus.ramdan.cdt.core.trx.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/cdt/core/trx/qr-code/query")
@Tag(name = "CR Code API", description = "APIs for query QR Code")
@RequiredArgsConstructor
@Getter
public class QRCodeQueryController implements BaseQueryController<QRCodeQueryDTO, UUID> {

    private final QRCodeQueryService service;

    @GetMapping("/code/{code}")
    public ResponseEntity<QRCodeQueryDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.getQRCodeByCode(code));
    }
}
