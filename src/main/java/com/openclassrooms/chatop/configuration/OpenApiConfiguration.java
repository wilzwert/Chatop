package com.openclassrooms.chatop.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wilhelm Zwertvaegher
 * Open API configuration : authentication type, contact, API title and license
 * This will be available at the Open API doc endpoint
 * ( for more information on this endpoint path,
 * see springdoc.api-docs.path in application.properties,
 * handled by SPRINGDOC_API_DOCS_PATH in .env )
 */
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(
                    new Info()
                            .title("Chatop API")
                            .version(appVersion)
                            .license(new License().name("Apache 2.0")
                            .url("https://springdoc.org"))
                            .contact(new Contact().email("wilzwert@gmail.com").name("Wilhelm Zwertvaegher"))
                );
    }
}
