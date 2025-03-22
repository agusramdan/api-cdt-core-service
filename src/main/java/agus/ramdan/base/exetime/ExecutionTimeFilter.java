package agus.ramdan.base.exetime;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
public class ExecutionTimeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            final long end = System.currentTimeMillis();
            response.addHeader("X-Execution-Time", String.format("%d ms",end - start));
            log.info("Request [{}] executed in {} ms", request.getRequestURI(), end - start);
        }
    }
}
