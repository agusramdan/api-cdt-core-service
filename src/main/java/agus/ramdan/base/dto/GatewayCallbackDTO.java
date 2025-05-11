package agus.ramdan.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayCallbackDTO {
    @JsonProperty("gateway_code")
    private String gatewayCode;
    @JsonProperty("transaction_no")
    private String transactionNo;
    private String status;
    private String message;
    private long timestamp;
    private Object data;
}
