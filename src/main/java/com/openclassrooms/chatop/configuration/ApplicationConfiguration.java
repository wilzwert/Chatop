package com.openclassrooms.chatop.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Wilhelm Zwertvaegher
 * Application configuration, only used to add .env as an external property source
 * .env allows the user to configure the application externally
 * Variables found in .env can be used in application.properties or in Environment
 */
@Configuration
@PropertySource("file:./.env")
@EnableConfigurationProperties({ ServerProperties.class, StorageProperties.class, ApiDocProperties.class})
public class ApplicationConfiguration {

}
