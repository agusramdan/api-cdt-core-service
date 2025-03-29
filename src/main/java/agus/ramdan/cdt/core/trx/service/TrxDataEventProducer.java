package agus.ramdan.cdt.core.trx.service;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.service.BaseCommandEntityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;

@Log4j2
public abstract class TrxDataEventProducer<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID>  implements BaseCommandEntityService<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    @Autowired
    private TrxDataEventProducerService trxDataEventProducerService;
    public void publishDataEvent(DataEvent dataEvent) {
        trxDataEventProducerService.publishDataEvent(dataEvent);
    }
}
