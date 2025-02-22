package agus.ramdan.base.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthorizationRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Mendapatkan RequestAttributes dari konteks saat ini
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return; // Tidak ada request aktif, maka tidak perlu menambahkan header
        }

        // Mengambil objek HttpServletRequest dari RequestAttributes
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // Mengambil nilai header "Authorization" dari request
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            // Menambahkan header "Authorization" ke request Feign
            requestTemplate.header("Authorization", authorizationHeader);
        }
    }
}
