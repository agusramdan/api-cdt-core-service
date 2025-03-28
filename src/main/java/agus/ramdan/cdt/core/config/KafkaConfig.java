package agus.ramdan.cdt.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration

public class KafkaConfig {
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Bean
    public ObjectMapper kafkaObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
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

        // Custom serializer untuk LocalDate
        javaTimeModule.addSerializer(LocalDate.class, new com.fasterxml.jackson.databind.JsonSerializer<>() {
            @Override
            public void serialize(LocalDate value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
                gen.writeString(value.format(LOCAL_DATE_FORMATTER));
            }
        });
        mapper.registerModule(javaTimeModule);
        return mapper;
    }

    @Bean
    public JsonSerializer<Object> kafkaJsonSerializer(ObjectMapper kafkaObjectMapper) {

        return new JsonSerializer<>(kafkaObjectMapper);
    }

    @Bean
    public JsonDeserializer<Object> kafkaJsonDeserializer(ObjectMapper kafkaObjectMapper) {
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class, kafkaObjectMapper);
        deserializer.addTrustedPackages("agus.ramdan*"); // Sesuaikan paket yang diperbolehkan
        return deserializer;
    }
}
