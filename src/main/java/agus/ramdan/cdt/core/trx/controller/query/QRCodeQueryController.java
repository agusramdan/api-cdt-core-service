package agus.ramdan.cdt.core.trx.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeQueryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class QRCodeQueryController implements BaseQueryController<QRCodeQueryDTO, String> {

    private final QRCodeQueryService service;

    @GetMapping("/code/{code}")
    public ResponseEntity<QRCodeQueryDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.getQRCodeByCode(code));
    }
    @GetMapping("/validate_by_id/{id}")
    @ApiResponses(value = {
            @ApiResponse(description = "successful repari active qr code operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
    })
    public ResponseEntity<QRCodeQueryDTO> validateId(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.validateQrCode(id));
    }

    @GetMapping("/validate_by_code/{code}")
    @ApiResponses(value = {
            @ApiResponse(description = "successful repari active qr code operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
    })
    public ResponseEntity<QRCodeQueryDTO> validateCode(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.OK).body(service.validateQrCode(code));
    }
}
