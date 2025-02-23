package agus.ramdan.base.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorValidation {
    private final String message;
    private final String key;
    private final Object value;

    public static ErrorValidation New(String message, String key, Object value){
        return new ErrorValidation(message, key, value);
    }

    public static ErrorValidation[] validations(ErrorValidation ... error){
        return error;
    }
}