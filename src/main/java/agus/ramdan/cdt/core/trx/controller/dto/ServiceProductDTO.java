package agus.ramdan.cdt.core.trx.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Informasi produk layanan terkait")
public class ServiceProductDTO {
    private String id;
    private String code;
    private String name;
}