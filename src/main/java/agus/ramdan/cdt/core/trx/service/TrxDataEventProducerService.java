package agus.ramdan.cdt.core.trx.service;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.dto.EventType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Log4j2
@RequiredArgsConstructor
@Service
public class TrxDataEventProducerService{
    private final KafkaTemplate<String, DataEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void publishDataEvent(DataEvent dataEvent) {
        try {
            String object = objectMapper.writeValueAsString(dataEvent.getData());
            TypeReference<HashMap<String,Object>> typeRef
                    = new TypeReference<HashMap<String,Object>>() {};
            val data =objectMapper.readValue(object,typeRef);
            log.info("data : {}",data);
            dataEvent.setData(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        publishDataEventAsync(dataEvent);
    }
    @Async("taskExecutor")
    public void publishDataEventAsync(DataEvent dataEvent) {
        kafkaTemplate.send("core-trx-event", dataEvent);
    }
    public void publishDataEvent(EventType type , Object object) {
        publishDataEvent(DataEvent.builder().data(object).eventType(type).dataType(object.getClass().getCanonicalName()).build());
    }
}
