package com.openclassrooms.chatop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Wilhelm Zwertvaegher
 * Server configuration properties
 * Used for application configuration (binding port)
 * and to generate URLs to uploaded images
 */
@ConfigurationProperties(prefix = "server")
@Getter
@Setter
public class ServerProperties {
    private String hostname;

    private String port;

    private String protocol;
}
