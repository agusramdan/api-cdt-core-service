package agus.ramdan.cdt.core.utils;

import agus.ramdan.base.exception.ErrorValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityFallbackFactory {

    public static <T> T safeGet(Callable<T> ex){
        try {
            return ex.call();
        } catch (Exception e) {
            if (e.getCause() instanceof EntityNotFoundException) {
                return createEntityFromException((EntityNotFoundException) e.getCause());
            }
            throw new RuntimeException("Failed to get entity", e);
        }
    }
    public static <T> T ensureNotLazy(@NotNull Collection<ErrorValidation> validations, String message, String key, Callable<T> ex) {
        try {
            return ex.call();
        } catch (Exception e) {
            if (e.getCause() instanceof EntityNotFoundException) {
                Pattern pattern = Pattern.compile("Unable to find ([\\w\\.]+) with id ([a-f0-9\\-]+)");
                Matcher matcher = pattern.matcher(e.getMessage());
                if (matcher.find()) {
                    String idValue = matcher.group(2);
                    ErrorValidation.add(validations, message, key, idValue);
                }
            } else {
                ErrorValidation.add(validations, message, key, e.getMessage());
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createEntityFromException(EntityNotFoundException ex) {
        String message = ex.getMessage();
        Pattern pattern = Pattern.compile("Unable to find ([\\w\\.]+) with id ([a-f0-9\\-]+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String className = matcher.group(1);
            String idValue = matcher.group(2);

            try {
                Class<T> clazz = (Class<T>) Class.forName(className);
                T instance = clazz.getDeclaredConstructor().newInstance();

                Field idField = null;
                Field nameField = null;

                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getName().equalsIgnoreCase("id")) {
                        idField= field;
                    }
                    if (field.getName().equalsIgnoreCase("name")) {
                        nameField= field;
                    }
                }
                if (idField != null) {
                    idField.setAccessible(true);
                    Class<?> idType = idField.getType();

                    if (idType.equals(UUID.class)) {
                        idField.set(instance, UUID.fromString(idValue));
                    } else if (idType.equals(String.class)) {
                        idField.set(instance, idValue);
                    } else {
                        throw new RuntimeException("Unsupported ID type: " + idType.getName());
                    }
                }
                if (nameField != null) {
                    nameField.setAccessible(true);
                    Class<?> idType = idField.getType();
                    if (idType.equals(String.class)) {
                        nameField.set(instance, "DELETED");
                    }
                }

                return instance;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create entity fallback", e);
            }
        }

        throw new IllegalArgumentException("Exception message does not match expected format");
    }
}
