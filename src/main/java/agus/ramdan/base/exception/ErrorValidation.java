package agus.ramdan.base.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Getter
@NoArgsConstructor
public class ErrorValidation {
    private String message;
    private String key;
    private Object value;

    public ErrorValidation(String message, String key, Object value) {
        this.message = message;
        this.key = key;
        this.value = value;
    }

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