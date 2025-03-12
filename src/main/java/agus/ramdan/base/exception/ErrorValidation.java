package agus.ramdan.base.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class ErrorValidation {
    private final String message;
    private final String key;
    private final Object value;

    public static ErrorValidation New(String message, String key, Object value) {
        return new ErrorValidation(message, key, value);
    }

    public static <T> T add(@NotNull Collection<ErrorValidation> collection, String message, String key, Object value) {
        collection.add(New(message, key, value));
        return null;
    }

    public static ErrorValidation[] validations(ErrorValidation... error) {
        return error;
    }
}