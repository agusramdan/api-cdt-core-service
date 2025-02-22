package agus.ramdan.base.exception;

import feign.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
@Getter
public class NoContentException extends RuntimeException implements FeighResponError{

    private static final long serialVersionUID = 1L;
    private Response response;
    public NoContentException(String message){
        super(message);
    }

    public NoContentException(String message, Response response) {
        super(message);
        this.response = response;
    }
}
