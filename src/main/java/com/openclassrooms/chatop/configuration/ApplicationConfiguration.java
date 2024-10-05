package com.openclassrooms.chatop.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Getter
public class ApplicationConfiguration implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    public ApplicationConfiguration(@Autowired StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+storageProperties.getUploadDir()+"/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/"+storageProperties.getUploadDir()+"/");
    }
}
