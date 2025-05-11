package agus.ramdan.cdt.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@Log4j2
public class KafkaConfig {
    @Bean
    public JsonSerializer<Object> kafkaJsonSerializer(ObjectMapper objectMapper) {
        log.info("Kafka Json Serializer. Kafka Object Mapper {}",objectMapper);
        return new JsonSerializer<>(objectMapper);
    }

    @Bean
    public JsonDeserializer<Object> kafkaJsonDeserializer(ObjectMapper objectMapper) {
        log.info("Kafka Json Deserializer. Kafka Object Mapper {}",objectMapper);
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class, objectMapper);
        deserializer.addTrustedPackages("agus.ramdan*"); // Sesuaikan paket yang diperbolehkan
        return deserializer;
    }
}
