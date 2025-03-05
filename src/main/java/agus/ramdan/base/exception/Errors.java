package agus.ramdan.base.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Errors {
    private Date timestamp;
    private String message;
    private String trace_id;
    private String span_id;
    private String details;
    private ErrorValidation[] errors;

    public Errors(Date timestamp, String message, String trace_id, String span_id, String details, ErrorValidation ... errors) {
        this.timestamp = timestamp;
        this.message = message;
        this.trace_id = trace_id;
        this.span_id = span_id;
        this.details = details;
        this.errors = errors;
    }

    @Schema(description = "Extension Error code")
    @JsonProperty("err_code")
    private String errCode;
}