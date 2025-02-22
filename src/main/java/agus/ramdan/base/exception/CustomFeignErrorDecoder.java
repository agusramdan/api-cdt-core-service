package agus.ramdan.base.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
@Log4j2
public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "Unknown error";
        Errors errors = null;

        try {
            if (response.body() != null) {
                message = response.body().toString();
                log.error(message);
            }
        } catch (Exception ignored) {}

        int status = response.status();
        if (status == HttpStatus.UNAUTHORIZED.value()){
            return new UnauthorizedException("Propagation UNAUTHORIZED",response);
        } else if (status == HttpStatus.BAD_REQUEST.value()) {
            return new BadRequestException("Propagation Bad Request");
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            return new ResourceNotFoundException("Propagation Not Found");
        } else {
            int status_group =status/100;
            if (status_group==4){
                return new Propagation3xxException("Need Validation",status,errors);
            }else if(status_group==3){
                return new Propagation3xxException("Redirection",status,errors);
            }
        }
        return new Propagation5xxException("Propagation Internal Server"+message,status,errors);
    }
}
