package com.example;

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
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS for all endpoints
                        .allowedOrigins("*") // Allow requests from any origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed headers
                        .maxAge(3600); // Cache preflight response for 1 hour
            }
        };
    }
}