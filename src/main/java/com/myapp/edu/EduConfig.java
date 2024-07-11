package com.myapp.edu;

import com.myapp.edu.auditor.AuditorAwareImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class EduConfig {

    private final HttpServletRequest httpServletRequest;
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl(httpServletRequest);
    }
}
