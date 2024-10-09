package com.openclassrooms.chatop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Wilhelm Zwertvaegher
 * Configuration for uploaded files storage, in particular the upload directory
 */
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String uploadDir;

}