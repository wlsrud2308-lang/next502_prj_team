package bitc.next502.team_prj.config;

import bitc.next502.team_prj.interceptor.BusinessAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private BusinessAuthInterceptor businessAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(businessAuthInterceptor)
                .addPathPatterns("/mng/**") // /mng로 시작하는 모든 요청 보호
                .excludePathPatterns("/login", "/signup"); // 로그인/회원가입 페이지는 제외
    }
}