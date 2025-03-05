package agus.ramdan.base.exception;

import lombok.Getter;

import java.util.Collection;

@Getter
public class PropagationXxxException extends XxxException {
    private static final long serialVersionUID = 1L;


    public PropagationXxxException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public PropagationXxxException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public PropagationXxxException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, code, errCode, cause, errors);
    }

    public PropagationXxxException(String message, int code, String errCode, Throwable cause, Collection<ErrorValidation> errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }
}
