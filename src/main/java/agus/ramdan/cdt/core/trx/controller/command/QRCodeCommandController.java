package agus.ramdan.cdt.core.trx.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.trx.controller.dto.deposit.TrxDepositQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeUpdateDTO;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeCommandService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/cdt/core/trx/qr-code/command")
@Tag(name = "QR Code Command API", description = "APIs for creating, updating, and deleting CR Code")
@RequiredArgsConstructor
@Getter
public class QRCodeCommandController implements BaseCommandController<QRCodeQueryDTO, QRCodeCreateDTO, QRCodeUpdateDTO, String> {
    private final QRCodeCommandService service;

    @PutMapping("/{id}/repair")
    @ApiResponses(value = {
            @ApiResponse(description = "successful repari active qr code operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositQueryDTO.class)),})
    })
    public ResponseEntity<QRCodeQueryDTO> repair(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.repair(id));
    }

}

