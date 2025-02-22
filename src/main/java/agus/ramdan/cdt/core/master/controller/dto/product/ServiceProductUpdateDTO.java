package agus.ramdan.cdt.core.master.controller.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO untuk memperbarui Service Product")
public class ServiceProductUpdateDTO {
    @Schema(description = "ID Produk dalam format String")
    private String id;

    @Schema(description = "Kode Produk")
    private String code;

    @Schema(description = "Nama Produk")
    private String name;
}
