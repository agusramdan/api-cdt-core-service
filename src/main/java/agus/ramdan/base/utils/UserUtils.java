package agus.ramdan.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserUtils {
    public static String username(String username) {
        return (username != null) ? username.replaceAll("[^a-zA-Z0-9]", "").toLowerCase() : null;
    }

}
