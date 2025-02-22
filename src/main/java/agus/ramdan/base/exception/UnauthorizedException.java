package agus.ramdan.base.exception;

import feign.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
@Getter
public class UnauthorizedException extends RuntimeException implements FeighResponError{
    private static final long serialVersionUID = 1L;
    private ErrorValidation[] errors;
    private Response response;
    public UnauthorizedException(String message){
        super(message);
    }

    public UnauthorizedException(String message, ErrorValidation[] errors) {
        super(message);
        this.errors = errors;
    }

    public UnauthorizedException(String message, Response response) {
        super(message);
        this.response = response;
    }
}
