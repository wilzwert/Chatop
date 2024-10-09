package com.openclassrooms.chatop.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Wilhelm Zwertvaegher
 * Date:09/10/2024
 * Time:11:38
 */
@ConfigurationProperties(prefix="springdoc")
@Getter
@Setter
public class ApiDocProperties {

    @NestedConfigurationProperty
    private ApiDocs apiDocs = new ApiDocs();
    public String getApiDocsPath() {
        return apiDocs.getPath();
    }

    @NestedConfigurationProperty
    private SwaggerUi swaggerUi = new SwaggerUi();

    public String getSwaggerPath() {
        return swaggerUi.getPath();
    }

    @Getter
    @Setter
    public static class ApiDocs {
        private String path;
    }

    @Getter
    @Setter
    public static class SwaggerUi {
        private String path;
    }
}
