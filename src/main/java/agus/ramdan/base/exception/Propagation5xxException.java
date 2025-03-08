package agus.ramdan.base.exception;

import lombok.Getter;

import java.util.Collection;

@Getter
public class Propagation5xxException extends PropagationXxxException {
    private static final long serialVersionUID = 1L;

    public Propagation5xxException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public Propagation5xxException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public Propagation5xxException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, code, errCode, cause, errors);
    }

    public Propagation5xxException(String message, int code, String errCode, Throwable cause, Collection<ErrorValidation> errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public Propagation5xxException(String message, int status, String errCode, Errors errors) {
        super(message, status, errCode, null,errors);
    }
}
