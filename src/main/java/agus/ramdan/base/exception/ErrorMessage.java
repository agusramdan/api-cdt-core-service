package agus.ramdan.base.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class ErrorMessage {
    public static String get(Throwable throwable,String msg){
        if (throwable==null){
            return msg;
        }
        if (throwable instanceof DataIntegrityViolationException){
            return get(throwable,"Data integrity violation");
        }
        if (throwable instanceof SQLException){
            return get(throwable,msg);
        }
        String error =get(throwable.getCause(),msg);
        if (error==null){
            log.error("Check Error",error);
        }
        return error;
    }
    public static String get(SQLException sqlException,String msg) {
        String message = sqlException.getMessage();
        if (message.contains("duplicate key value")) {
            msg = extractDuplicateKeyMessage(message);
        }
        return msg;
    }

    public static String extractDuplicateKeyMessage(String message) {
        // Pola regex untuk menangkap "Key (msisdn)=(+6281234567890) already exists."
        Pattern pattern = Pattern.compile("Key \\((.*?)\\)=\\((.*?)\\) already exists");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return "Key (" + matcher.group(1) + ")=(" + matcher.group(2) + ") already exists.";
        }
        return "Duplicate key constraint violation.";
    }
}