package agus.ramdan.cdt.core.trx.controller;

import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cdtCoreTrxClient", url = "${feign-client.core-trx.base-url}/qr-code")
public interface CdtCoreTrxQRCodeClient {

    @GetMapping("/query/code/{code}")
    QRCodeQueryDTO getByCode(@PathVariable String code);

}
