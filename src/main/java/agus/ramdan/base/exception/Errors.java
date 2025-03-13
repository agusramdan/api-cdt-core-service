package agus.ramdan.base.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Errors {
    private Date timestamp;
    private String message;
    @JsonProperty("trace_id")
    private String traceId;
    @JsonProperty("span_id")
    private String spanId;
    private String details;
    private ErrorValidation[] errors;

    public Errors(Date timestamp, String message, String traceId, String spanId, String details, ErrorValidation... errors) {
        this.timestamp = timestamp;
        this.message = message;
        this.traceId = traceId;
        this.spanId = spanId;
        this.details = details;
        this.errors = errors;
    }

    @Schema(description = "Extension Error code")
    @JsonProperty("err_code")
    private String errCode;
    @JsonProperty("request_body")
    private Object requestBody;
}