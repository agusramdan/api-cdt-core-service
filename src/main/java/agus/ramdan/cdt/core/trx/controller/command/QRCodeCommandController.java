package agus.ramdan.cdt.core.trx.controller.command;

import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeCreateDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeUpdateDTO;
import agus.ramdan.cdt.core.trx.service.qrcode.QRCodeCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/trx/qr-code/command")
@Tag(name = "QR Code Command API", description = "APIs for creating, updating, and deleting CR Code")
@RequiredArgsConstructor
@Getter
public class QRCodeCommandController implements BaseCommandController<QRCodeQueryDTO, QRCodeCreateDTO, QRCodeUpdateDTO, String> {
    private final QRCodeCommandService service;

}

