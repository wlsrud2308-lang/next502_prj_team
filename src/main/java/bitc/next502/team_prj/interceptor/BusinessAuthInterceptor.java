package bitc.next502.team_prj.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BusinessAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false); // 세션 없으면 null
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }

        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");

        if (userBoxing == null || role == null || !"BUSINESS".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true; // 권한 있음 → 컨트롤러 진행
    }
}
