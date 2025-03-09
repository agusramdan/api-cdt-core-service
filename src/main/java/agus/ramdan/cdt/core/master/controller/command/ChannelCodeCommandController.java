package agus.ramdan.cdt.core.master.controller.command;
import agus.ramdan.base.controller.BaseCommandController;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeCreateDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeQueryDTO;
import agus.ramdan.cdt.core.master.controller.dto.channelcode.ChannelCodeUpdateDTO;
import agus.ramdan.cdt.core.master.service.channelcode.ChannelCodeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cdt/core/master/channel-code/command")
@RequiredArgsConstructor
public class ChannelCodeCommandController implements
        BaseCommandController<ChannelCodeQueryDTO, ChannelCodeCreateDTO, ChannelCodeUpdateDTO, String> {

    private final ChannelCodeCommandService service;

    @Override
    public ChannelCodeCommandService getService() {
        return service;
    }
}
