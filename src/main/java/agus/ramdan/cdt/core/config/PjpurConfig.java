package agus.ramdan.cdt.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "pjpur")
public class PjpurConfig {
    // Blocking where the transaction is not allowed to be processed without status PJPUR_SUCCESS
    private boolean trxBlocking;
    private boolean online;
}
