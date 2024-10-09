package com.openclassrooms.chatop.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Wilhelm Zwertvaegher
 * Configuration for uploaded files handling
 */
@Configuration
@Getter
public class StorageConfiguration implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    public StorageConfiguration(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    /**
     *  Set resource handler for uploaded files
     * @param registry the RessourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+storageProperties.getUploadDir()+"/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/"+storageProperties.getUploadDir()+"/");
    }
}