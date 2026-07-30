package com.fernando.carrotback.infrastructure.config;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                  .allowedOrigins("*")
                  .allowedMethods(
                    "GET",
                    "POST"
                  )
                  .allowedHeaders("*")
                  .allowCredentials(false);
            }
        };
    }
}
