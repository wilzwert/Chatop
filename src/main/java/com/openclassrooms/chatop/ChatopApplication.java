package com.openclassrooms.chatop;

import com.openclassrooms.chatop.configuration.ApplicationConfiguration;
import com.openclassrooms.chatop.configuration.ServerProperties;
import com.openclassrooms.chatop.configuration.StorageProperties;
import com.openclassrooms.chatop.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({ ServerProperties.class, StorageProperties.class})
public class ChatopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
