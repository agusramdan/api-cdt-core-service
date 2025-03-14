package agus.ramdan.base.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "Unknown error";
        int status = response.status();
        if (status == HttpStatus.UNAUTHORIZED.value()) {
            return new UnauthorizedException("Propagation UNAUTHORIZED", response);
        }
        if (status == HttpStatus.NOT_FOUND.value()) {
            return new ResourceNotFoundException("Not Found");
        }
        Errors errors = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            errors = mapper.readValue(bodyIs, Errors.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        if(errors!=null) {
            if (status == HttpStatus.BAD_REQUEST.value()) {
                return new BadRequestException(errors.getMessage(), errors);
            } else {
                int status_group = status / 100;
                if (status_group == 4) {
                    return new Propagation4xxException("Need Validation", status, errors.getErrCode(), null, errors);
                } else if (status_group == 3) {
                    return new Propagation3xxException("Redirection", status, errors.getErrCode(), null, errors);
                }
            }
            return new Propagation5xxException(errors.getMessage(), status, errors.getErrCode(), errors);
        }else {
            if (status == HttpStatus.BAD_REQUEST.value()) {
                return new BadRequestException("Bad Request");
            } else {
                int status_group = status / 100;
                if (status_group == 4) {
                    return new Propagation4xxException("Need Validation", status);
                } else if (status_group == 3) {
                    return new Propagation3xxException("Redirection", status);
                }
            }
            return new Propagation5xxException("Error", status,null);

        }
    }
}
