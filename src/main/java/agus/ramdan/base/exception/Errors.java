package agus.ramdan.base.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Errors {
    private Date timestamp;
    @Schema(description = "HTTP Status Code")
    private int status;
    @Schema(description = "HTTP Status Message")
    private String error;
    @Schema(description = "Application Error Trace")
    private String trace;
    @Schema(description = "Application Error Message")
    private String message;
    @Schema(description = "Request Path")
    private String path;
    @JsonProperty("trace_id")
    private String traceId;
    @JsonProperty("span_id")
    private String spanId;
    private String details;
    private ErrorValidation[] errors;

    public Errors(Date timestamp, String message, String traceId, String spanId, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.traceId = traceId;
        this.spanId = spanId;
        this.path = path;
        this.details = path;
    }
    public Errors(String message,Object requestBody) {
        this.timestamp = new Date();
        this.message = message;
        this.requestBody=requestBody;
    }
    public Errors(String message,ErrorValidation... errors) {
        this.timestamp = new Date();
        this.message = message;
        this.errors = errors;
    }
    public void setup(int status,String traceId, String spanId, String path){
        this.status = status;
        this.traceId = traceId;
        this.spanId = spanId;
        this.path = path;
    }
    @Schema(description = "Extension Error code")
    @JsonProperty("err_code")
    private String errCode;
    @JsonProperty("request_body")
    private Object requestBody;
}