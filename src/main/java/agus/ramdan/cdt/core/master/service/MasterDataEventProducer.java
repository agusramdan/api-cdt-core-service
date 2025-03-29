package agus.ramdan.cdt.core.master.service;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.service.BaseCommandEntityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.HashMap;

@Log4j2
public abstract class MasterDataEventProducer<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID>  implements BaseCommandEntityService<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    @Autowired
    private KafkaTemplate<String, DataEvent> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public void publishDataEvent(DataEvent dataEvent) {
        try {
            String str = objectMapper.writeValueAsString(dataEvent.getData());
            TypeReference<HashMap<String,Object>> typeRef
                    = new TypeReference<HashMap<String,Object>>() {};
            dataEvent.setData(objectMapper.readValue(str,typeRef));
            log.info("STR data {} ",str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("core-master-event", dataEvent);
    }
}
