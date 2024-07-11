package com.myapp.edu;

import com.myapp.edu.argumentresolver.InstructorArgumentResolver;
import com.myapp.edu.auditor.AuditorAwareImpl;
import com.myapp.edu.interceptor.LoginCheckInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class EduConfig implements WebMvcConfigurer {

    private final HttpServletRequest httpServletRequest;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl(httpServletRequest);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/auth/login", "/auth/logout", "/members", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new InstructorArgumentResolver());
    }

}
