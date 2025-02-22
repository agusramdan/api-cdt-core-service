package agus.ramdan.base.exception;

import lombok.Getter;

@Getter
public class Propagation3xxException extends PropagationXxxException {
    private static final long serialVersionUID = 1L;
    public Propagation3xxException(String message, int code, Errors errors) {
        super(message,code,errors);
    }

}
