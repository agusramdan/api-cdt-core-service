package agus.ramdan.base.utils;

import agus.ramdan.base.exception.ErrorValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class EntityFallbackFactory {
    public static <T> T safe(T t){
        try {
            Hibernate.initialize(t);
        } catch (Exception e) {
            t = createEntityFromException(e);
            if (t== null) {
                log.error("EntityFallbackFactory safe", e);
            }
        }
        return t;
    }
    public static <T> T ensureNotLazy(@NotNull Collection<ErrorValidation> validations, String message, String key, Callable<T> ex) {
        try {
            T result = ex.call();
            Hibernate.initialize(result);
            return result;
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
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
    public static <T> T createEntityFromException(Throwable e){
        while (e!=null){
            try {
                log.warn(e.getMessage());
                return createEntityFromException(e.getMessage());
            }catch (Exception ex){
                e= e.getCause();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createEntityFromException(String message) {
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
                Field codeField = null;

                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getName().equalsIgnoreCase("id")) {
                        idField= field;
                    }
                    if (field.getName().equalsIgnoreCase("name")) {
                        nameField= field;
                    }
                    if (field.getName().equalsIgnoreCase("code")) {
                        codeField= field;
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
                    Class<?> idType = nameField.getType();
                    if (idType.equals(String.class)) {
                        nameField.set(instance, "DELETED");
                    }
                }
                if (codeField != null) {
                    codeField.setAccessible(true);
                    Class<?> idType = codeField.getType();
                    if (idType.equals(String.class)) {
                        codeField.set(instance, "DELETED");
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
