package agus.ramdan.cdt.core.trx.controller.client;


import agus.ramdan.base.client.BaseQueryClient;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feign-client.api-cdm-core-trx-query-service.qr-code", url = "${feign-client.api-cdm-core-trx-query-service.base-url}/api/cdt/core/trx/qr-code/query")
public interface QRCodeQueryClient extends BaseQueryClient<QRCodeQueryDTO, String> {

    @GetMapping("/code/{code}")
    QRCodeQueryDTO getByCodeDTO(@PathVariable String code);
}
