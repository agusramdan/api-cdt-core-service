package agus.ramdan.cdt.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // Custom serializer untuk LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new com.fasterxml.jackson.databind.JsonSerializer<>() {
            @Override
            public void serialize(LocalDateTime value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
                gen.writeString(value.format(LOCAL_DATE_TIME_FORMATTER));
            }
        });

        // Custom serializer untuk LocalTime
        javaTimeModule.addSerializer(LocalTime.class, new com.fasterxml.jackson.databind.JsonSerializer<>() {
            @Override
            public void serialize(LocalTime value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
                gen.writeString(value.format(LOCAL_TIME_FORMATTER));
            }
        });

        mapper.registerModule(javaTimeModule);
        return mapper;
    }
}
