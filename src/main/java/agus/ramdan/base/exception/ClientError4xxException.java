package agus.ramdan.base.exception;

import lombok.Getter;

//4xx
@Getter
public class ClientError4xxException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int code;
    private final ErrorValidation[] errors;
    public ClientError4xxException(String message,int code,ErrorValidation[] errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

}
