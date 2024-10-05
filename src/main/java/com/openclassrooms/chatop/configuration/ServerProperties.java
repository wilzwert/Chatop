package com.openclassrooms.chatop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
@Getter
@Setter
public class ServerProperties {
    private String hostname;

    private String port;

    private String protocol;
}
