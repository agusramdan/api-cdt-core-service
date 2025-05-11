package agus.ramdan.cdt.core.master.service;

import agus.ramdan.base.dto.DataEvent;
import agus.ramdan.base.service.BaseCommandEntityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public abstract class MasterDataEventProducer<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID>  implements BaseCommandEntityService<T, ID, ResultDTO, CreateDTO, UpdateDTO, DTO_ID> {
    @Autowired
    private MasterDataEventProducerService masterDataEventProducerService;

    public void publishDataEvent(DataEvent dataEvent) {
        masterDataEventProducerService.publishDataEvent(dataEvent);
    }
}
