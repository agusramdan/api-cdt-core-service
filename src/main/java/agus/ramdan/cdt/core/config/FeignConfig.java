package agus.ramdan.cdt.core.config;

import agus.ramdan.base.exception.CustomFeignErrorDecoder;
import agus.ramdan.base.interceptor.AuthorizationRequestInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {
        "agus.ramdan.cdt.core.gateway.controller.client"}
)
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new AuthorizationRequestInterceptor();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
