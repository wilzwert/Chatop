package com.openclassrooms.chatop.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Getter
public class ApplicationConfiguration implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    public ApplicationConfiguration(@Autowired StorageProperties storageProperties, @Autowired WebProperties webProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("addResourceHandlers"+"/"+storageProperties.getUploadDir()+"/**"+" -- "+"file:" + System.getProperty("user.dir") + "/"+storageProperties.getUploadDir()+"/");
        registry.addResourceHandler("/"+storageProperties.getUploadDir()+"/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/"+storageProperties.getUploadDir()+"/");
    }
}
