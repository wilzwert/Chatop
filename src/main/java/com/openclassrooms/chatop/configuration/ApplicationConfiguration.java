package com.openclassrooms.chatop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:./.env")
public class ApplicationConfiguration {

}
