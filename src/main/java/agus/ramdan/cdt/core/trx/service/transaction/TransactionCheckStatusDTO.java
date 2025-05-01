package agus.ramdan.cdt.core.trx.service.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@Builder
public class TransactionCheckStatusDTO {
    @JsonProperty(index = 1)
    private UUID id;
    private String source;
    private String message;
}
