package agus.ramdan.base.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
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
                errors = new ObjectMapper().readValue(response.body().asInputStream(), Errors.class);
            }
        } catch (Exception ignored) {}

        int status = response.status();
        if (status == HttpStatus.UNAUTHORIZED.value()){
            return new UnauthorizedException("Propagation UNAUTHORIZED",response);
        } else if (status == HttpStatus.BAD_REQUEST.value()) {
            if (errors!=null) {
                return new BadRequestException(errors.getMessage(), errors);
            }else {
                return new BadRequestException("Bad Request");
            }
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            return new ResourceNotFoundException("Not Found");
        } else {
            int status_group =status/100;
            if (status_group==4){
                if (errors!=null) {
                    return new Propagation3xxException("Need Validation",status,String.valueOf(status),null,errors);
                }else {
                    return new Propagation3xxException("Need Validation",status);
                }
            }else if(status_group==3){
                return new Propagation3xxException("Redirection",status,String.valueOf(status),null,errors);
            }
        }
        return new Propagation5xxException("Propagation Internal Server"+message,status,null,errors);
    }
}
