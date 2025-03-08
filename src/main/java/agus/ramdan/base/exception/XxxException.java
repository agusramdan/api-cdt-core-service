package agus.ramdan.base.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Date;

@Getter
public class XxxException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int code;
    private final Errors errors;
    private final ErrorValidation[] errorValidations;
    private final String errCode;
    public XxxException(String message, int code,Throwable cause) {
        this(message,code,String.valueOf(code),cause);
    }
//    public XxxException(String message, int code, String errCode,Throwable cause) {
//        super(message,cause);
//        this.code = code;
//        this.errCode =errCode;
//        this.errors = null;
//        this.errorValidations = null;
//    }
    public XxxException(String message, int code, String errCode,Throwable cause,ErrorValidation ... errorValidations) {
        super(message,cause);
        this.errCode =errCode;
        this.code = code;
        this.errorValidations = errorValidations;
        this.errors = null;
    }
    public XxxException(String message, int code, String errCode,Throwable cause,Errors errors) {
        super(message,cause);
        this.errCode =errCode;
        this.code = code;
        this.errors = errors;
        this.errorValidations = errors!=null? errors.getErrors():null;
    }

    public XxxException(String message, int code, String errCode, Throwable cause, @NotNull Collection<ErrorValidation> errorValidations) {
        this(message,code,errCode,cause,errorValidations.toArray(new ErrorValidation[0]));
    }

    public Errors create(String trace_id, String span_id, String details){
        Errors errors = this.errors;
        ErrorValidation[] errorValidations=null;
        if (errors!=null){
            errorValidations = errors.getErrors();
            details = String.format("%s,details=%s",details,this.errors.getDetails());
        }
        if (errorValidations==null){
            errorValidations = this.errorValidations;
        }

        errors = new Errors(new Date(),getMessage(),trace_id,span_id,details,errorValidations);
        errors.setErrCode(this.errCode);
        return errors;
    }
    public HttpStatus getHttpStatus(){
        return HttpStatus.resolve(code);
    }
}
