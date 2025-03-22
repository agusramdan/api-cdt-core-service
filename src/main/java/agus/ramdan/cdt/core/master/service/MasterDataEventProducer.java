package agus.ramdan.cdt.core.master.service;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.service.BaseCommandEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class MasterDataEventProducer<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID>  implements BaseCommandEntityService<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    @Autowired
    private KafkaTemplate<String, DataEvent> kafkaTemplate;
    public void publishDataEvent(DataEvent dataEvent) {
        kafkaTemplate.send("core-master-event", dataEvent);
    }
}
