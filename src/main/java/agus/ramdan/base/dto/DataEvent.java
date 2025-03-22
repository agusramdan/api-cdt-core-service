package agus.ramdan.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DataEvent {
    @JsonProperty("event_type")
    private EventType eventType;
    @JsonProperty("data_type")
    private String dataType;
    private Object data;
}
