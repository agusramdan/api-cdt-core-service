package agus.ramdan.cdt.core.trx;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.service.BaseCommandEntityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.HashMap;

public abstract class TrxDataEventProducer<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID>  implements BaseCommandEntityService<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    @Autowired
    private KafkaTemplate<String, DataEvent> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public void publishDataEvent(DataEvent dataEvent) {
        try {
            byte[] object = objectMapper.writeValueAsBytes(dataEvent.getData());
            TypeReference<HashMap<String,Object>> typeRef
                    = new TypeReference<HashMap<String,Object>>() {};
            dataEvent.setData(objectMapper.readValue(object,typeRef));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("core-trx-event", dataEvent);
    }
}
