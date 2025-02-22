package agus.ramdan.base.exception;

import feign.Response;

public interface FeighResponError {
    Response getResponse();
}
