package com.example.epolsoftbackend.another;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/library")
                .excludePathPatterns("/api/file/download/")
                .excludePathPatterns("/api/authors/signup")
                .excludePathPatterns("/api/authors/signin");
    }
}
