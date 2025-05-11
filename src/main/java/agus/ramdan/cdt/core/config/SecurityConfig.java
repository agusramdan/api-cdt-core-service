package agus.ramdan.cdt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Profile("oauth2")
@Configuration
public class SecurityConfig {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("JavaInUse Authentication Service"))
//                .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
//                .components(new Components().addSecuritySchemes("JavaInUseSecurityScheme",
//                        new SecurityScheme()
//                        .name("JavaInUseSecurityScheme")
//                        .type(SecurityScheme.Type.OAUTH2).scheme("basic")));
//    }
    // @formatter:off
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**","/swagger-ui.html","/swagger-ui/**", "/v3/api-docs/**","/v3/api-docs.yaml").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/cdt/core/trx/qr-code/code").hasAuthority("SCOPE_cdm.read")

                        .requestMatchers(HttpMethod.GET,"/api/cdt/core/master/**","/api/cdt/core/trx/**").hasAnyAuthority("SCOPE_cdm.read","SCOPE_web.internal.read","SCOPE_admin.internal.read")
                        .requestMatchers(HttpMethod.POST,"/api/cdt/core/master/**","/api/cdt/core/trx/qr-code/**").hasAnyAuthority("SCOPE_web.internal.create","SCOPE_admin.internal.create")
                        .requestMatchers(HttpMethod.PUT,"/api/cdt/core/master/**","/api/cdt/core/trx/qr-code/**").hasAnyAuthority("SCOPE_web.internal.update","SCOPE_admin.internal.update")
                        .requestMatchers(HttpMethod.DELETE,"/api/cdt/core/master/**","/api/cdt/core/trx/qr-code/**").hasAnyAuthority("SCOPE_web.internal.delete","SCOPE_admin.internal.delete")

                        .requestMatchers(HttpMethod.GET,"/api/cdt/core/trx/deposit/**").hasAuthority("SCOPE_cdm.read")
                        .requestMatchers(HttpMethod.POST,"/api/cdt/core/trx/deposit/**").hasAuthority("SCOPE_cdm.write")
                        .requestMatchers(HttpMethod.PUT,"/api/cdt/core/trx/deposit/**").hasAuthority("SCOPE_cdm.write")
                        .requestMatchers(HttpMethod.DELETE,"/api/cdt/core/trx/deposit/**").hasAuthority("SCOPE_admin.internal.delete")

                        .requestMatchers(HttpMethod.GET,"/api/cdt/core/trx/service-transaction/**").hasAnyAuthority("SCOPE_web.internal.read","SCOPE_admin.internal.read")
                        .requestMatchers(HttpMethod.PUT,"/api/cdt/core/trx/service-transaction/**").hasAnyAuthority("SCOPE_web.internal.update","SCOPE_admin.internal.update")

                        .requestMatchers(HttpMethod.GET,"/api/cdt/core/trx/pickup/**").hasAuthority("SCOPE_cdm.read")
                        .requestMatchers(HttpMethod.POST,"/api/cdt/core/trx/pickup/**").hasAuthority("SCOPE_cdm.write")
                        .requestMatchers(HttpMethod.PUT,"/api/cdt/core/trx/pickup/**").hasAuthority("SCOPE_cdm.write")
                        .requestMatchers(HttpMethod.DELETE,"/api/cdt/core/trx/pickup/**").hasAuthority("SCOPE_admin.internal.delete")

                        .anyRequest().authenticated()
            ).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }
    // @formatter:on

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
