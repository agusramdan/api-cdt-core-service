package agus.ramdan.base.exception;

import lombok.Getter;

import java.util.Collection;

//4xx
@Getter
public class ClientError4xxException extends PropagationXxxException {
    private static final long serialVersionUID = 1L;

    public ClientError4xxException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public ClientError4xxException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public ClientError4xxException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, code, errCode, cause, errors);
    }

    public ClientError4xxException(String message, int code, String errCode, Throwable cause, Collection<ErrorValidation> errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }
}
