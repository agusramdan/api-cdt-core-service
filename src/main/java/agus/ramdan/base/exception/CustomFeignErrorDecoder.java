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
                errors = new ObjectMapper().readValue(response.body().asInputStream(), Errors.class);
            }
        } catch (Exception ignored) {
        }

        int status = response.status();
        if (status == HttpStatus.UNAUTHORIZED.value()) {
            return new UnauthorizedException("Propagation UNAUTHORIZED", response);
        } else if (status == HttpStatus.BAD_REQUEST.value()) {
            if (errors != null) {
                return new BadRequestException(errors.getMessage(), errors);
            } else {
                return new BadRequestException("Bad Request");
            }
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            return new ResourceNotFoundException("Not Found");
        } else {
            int status_group = status / 100;
            if (status_group == 4) {
                if (errors != null) {
                    return new Propagation4xxException("Need Validation", status, errors.getErrCode(), null, errors);
                } else {
                    return new Propagation4xxException("Need Validation", status);
                }
            } else if (status_group == 3) {
                if (errors != null) {
                    return new Propagation3xxException("Redirection", status, errors.getErrCode(), null, errors);
                } else {
                    return new Propagation3xxException("Redirection", status);
                }
            }
        }
        if (errors != null) {
            return new Propagation5xxException(errors.getMessage(), status, errors.getErrCode(), errors);
        }
        return new Propagation5xxException("Propagation Internal Server" + message, status, null, errors);
    }
}
