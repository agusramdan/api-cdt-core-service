package agus.ramdan.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends ClientError4xxException {
    private static final long serialVersionUID = 1L;
    public BadRequestException(String message) {
        this(message,null);
    }
    public BadRequestException(String message, ErrorValidation[] errors) {
        this(message,HttpStatus.BAD_REQUEST.value(),"400",null,errors);
    }

    public BadRequestException(String message,ErrorValidation[] errors, Exception e) {
        this(message, HttpStatus.BAD_REQUEST.value(),"400", e, errors);
    }

    public BadRequestException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public BadRequestException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public BadRequestException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, code, errCode, cause, errors);
    }

    public BadRequestException(String message, int code, String errCode, Throwable cause, Collection<ErrorValidation> errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }
}
