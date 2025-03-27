package agus.ramdan.cdt.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Bean
    public ObjectMapper kafkaObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return objectMapper;
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
