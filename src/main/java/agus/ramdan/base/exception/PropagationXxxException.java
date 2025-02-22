package agus.ramdan.base.exception;

import lombok.Getter;

@Getter
public class PropagationXxxException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int code;
    private final Errors errors;
    public PropagationXxxException(String message, int code, Errors errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

}
