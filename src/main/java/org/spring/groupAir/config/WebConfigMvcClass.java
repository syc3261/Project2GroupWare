package org.spring.groupAir.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigMvcClass implements WebMvcConfigurer {

    String saveFiles = "file:///c:/groupAir/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/***")
            .addResourceLocations(saveFiles);
    }

}
