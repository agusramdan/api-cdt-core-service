package agus.ramdan.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCheckStatusDTO {
    @JsonProperty(index = 1)
    private UUID id;
    private String source;
    private String message;
}
