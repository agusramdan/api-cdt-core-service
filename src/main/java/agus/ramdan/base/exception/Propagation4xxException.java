package agus.ramdan.base.exception;

import lombok.Getter;

import java.util.Collection;

@Getter
public class Propagation4xxException extends PropagationXxxException {
    private static final long serialVersionUID = 1L;


    public Propagation4xxException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public Propagation4xxException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public Propagation4xxException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, code, errCode, cause, errors);
    }

    public Propagation4xxException(String message, int code, String errCode, Throwable cause, Collection<ErrorValidation> errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }
}
