package agus.ramdan.base.utils;

import agus.ramdan.base.exception.NoContentException;
import agus.ramdan.base.exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Streamable;

import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChekUtils {
    public static void ifEmptyThrow(Streamable page) throws NoContentException {
        if (page.isEmpty()) {
            throw new NoContentException(null);
        }
    }

    public static <T> T getOrThrow(Optional<T> optional, Supplier<String> msg) throws ResourceNotFoundException {
        return optional.orElseThrow(() -> new ResourceNotFoundException(msg.get()));
    }
}
