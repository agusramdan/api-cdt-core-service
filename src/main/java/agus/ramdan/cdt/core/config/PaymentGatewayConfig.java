package agus.ramdan.cdt.core.config;

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
//    private String apiKey;
//
//    private ApiUrlConfig localTransfer;
//
////    private String merchantId;
////    private String transactionKey;
////    private ServiceConfig service;
//
//    @Getter
//    @Setter
//    public static class ServiceConfig {
//        private ApiUrlConfig checkAccountInquiry;
//        private TransferBalanceConfig transferBalance;
//    }
//    @Getter
//    @Setter
//    public static class ApiUrlConfig {
//        private String path;
//    }
//
//    @Getter
//    @Setter
//    public static class TransferBalanceConfig {
//        private String apiUrl;
//        /**
//         * 0 = Transfer Online
//         * 1 = BI Fast Transfer
//         */
//        private String transferType = "0";
//    }
}


