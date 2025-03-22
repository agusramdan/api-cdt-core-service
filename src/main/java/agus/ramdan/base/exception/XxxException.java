package agus.ramdan.base.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Getter
@Log4j2
public class XxxException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int code;
    private final Errors errors;
    private final ErrorValidation[] errorValidations;
    private final String errCode;

    public XxxException(String message, int code, Throwable cause) {
        this(message, code, String.valueOf(code), cause);
    }

    //    public XxxException(String message, int code, String errCode,Throwable cause) {
//        super(message,cause);
//        this.code = code;
//        this.errCode =errCode;
//        this.errors = null;
//        this.errorValidations = null;
//    }
    public XxxException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, cause);
        this.errCode = errCode;
        this.code = code;
        this.errorValidations = errorValidations;
        this.errors = null;
    }

    public XxxException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, cause);
        this.errCode = errCode;
        this.code = code;
        this.errors = errors;
        this.errorValidations = errors != null ? errors.getErrors() : null;
    }

    public XxxException(String message, int code, String errCode, Throwable cause, @NotNull Collection<ErrorValidation> errorValidations) {
        this(message, code, errCode, cause, errorValidations.toArray(new ErrorValidation[0]));
    }
    public Errors getOrCreate(String trace_id, String span_id, String path) {
        Errors errors = this.errors;
        if (errors != null) {
            if (errors.getDetails()!=null) {
                errors.setDetails(String.format("%s,details=%s", path, errors.getDetails()));
            }
            if(errors.getErrCode()==null){
                errors.setErrCode(this.errCode);
            }
        }else {
            errors = new Errors(getMessage(),this.errorValidations);
            errors.setErrCode(this.errCode);
        }
        errors.setup(code, trace_id, span_id, path);
        if (!log.isTraceEnabled()) {
            errors.setTrace(null);
        }
        if (!log.isDebugEnabled()) {
            errors.setRequestBody(null);
        }
        return errors;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.resolve(code);
    }
}
