package agus.ramdan.cdt.core.master.controller.query;

import agus.ramdan.base.controller.BaseQueryController;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeQueryDTO;
import agus.ramdan.cdt.core.master.service.channelcode.ChannelCodeQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/channel-code/query")
@Tag(name = "ChannelCode Query API", description = "APIs for query ChannelCode")
@RequiredArgsConstructor
@Getter
public class ChannelCodeQueryController implements
        BaseQueryController<ChannelCodeQueryDTO, String> {
    private final ChannelCodeQueryService service;

}
