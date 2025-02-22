package agus.ramdan.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends ClientError4xxException {
    private static final long serialVersionUID = 1L;
    public BadRequestException(String message) {
        super(message,HttpStatus.BAD_REQUEST.value(),null);
    }

    public BadRequestException(String message, ErrorValidation[] errors) {
        super(message,HttpStatus.BAD_REQUEST.value(),errors);
    }
}
