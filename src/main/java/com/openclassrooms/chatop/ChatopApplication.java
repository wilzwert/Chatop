package com.openclassrooms.chatop;

import com.openclassrooms.chatop.configuration.OpenApiConfiguration;
import com.openclassrooms.chatop.configuration.ServerProperties;
import com.openclassrooms.chatop.configuration.StorageProperties;
import com.openclassrooms.chatop.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Wilhelm Zwertvaegher
 */
@SpringBootApplication
public class ChatopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> storageService.init();
    }
}
