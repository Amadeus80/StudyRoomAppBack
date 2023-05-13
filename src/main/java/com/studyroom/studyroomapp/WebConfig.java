package com.studyroom.studyroomapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${allowed.url}")
    private String urlAllowed;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println(urlAllowed);
        registry.addMapping("/**")
            .allowedOrigins(urlAllowed)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
