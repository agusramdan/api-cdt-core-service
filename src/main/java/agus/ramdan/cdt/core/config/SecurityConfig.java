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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html","/swagger-ui/**", "/v3/api-docs/**","/v3/api-docs.yaml").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/cdt/cdm/**").hasAnyAuthority("SCOPE_read","SCOPE_cdm.read")
                        .requestMatchers(HttpMethod.POST,"/api/cdt/cdm/**").hasAnyAuthority("SCOPE_write","SCOPE_cdm.write")
                        .requestMatchers(HttpMethod.PUT,"/api/cdt/cdm/**").hasAnyAuthority("SCOPE_write","SCOPE_cdm.write")
                        .requestMatchers(HttpMethod.DELETE,"/api/cdt/cdm/**").hasAnyAuthority("SCOPE_delete","SCOPE_cdm.delete")
                        .anyRequest().authenticated()
            ).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
