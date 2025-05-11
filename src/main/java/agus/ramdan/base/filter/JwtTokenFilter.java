package agus.ramdan.base.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@Profile("!oauth2")
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        val authentication_old = SecurityContextHolder.getContext().getAuthentication();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .build() // Tidak menggunakan setSigningKey
                    .parseClaimsJwt(token) // Perhatikan: `parseClaimsJwt` bukan `parseClaimsJws`
                    .getBody();
            String userId = claims.getSubject();
            // Set Authentication ke SecurityContext
            // Object object = claims.get("Role");
            //val authentication = new UsernamePasswordAuthenticationToken(userId, token, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(userId, token, new ArrayList<>()));
        }
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication_old);
    }
}
