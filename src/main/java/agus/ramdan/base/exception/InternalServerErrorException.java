package agus.ramdan.base.exception;

import feign.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class InternalServerErrorException extends RuntimeException implements FeighResponError{

    private static final long serialVersionUID = 1L;
    private Response response;
    public InternalServerErrorException(String message){
        super(message);
    }

    public InternalServerErrorException(String message, Response response) {
        super(message);
        this.response = response;
    }
}
