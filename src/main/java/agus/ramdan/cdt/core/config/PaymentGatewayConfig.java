package agus.ramdan.cdt.core.config;

import agus.ramdan.cdt.core.trx.persistence.domain.TrxTransferStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "payment-gateway")
public class PaymentGatewayConfig {
    private String code;
    private TrxTransferStatus transferStatus = TrxTransferStatus.SUCCESS;
}


