package com.zzq.swagger.config;


import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {


    @Bean
    OpenApiCustomiser openApiCustomiser() {
        final String securitySchemeName = "Token";

        return openApi -> {
            openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
            openApi.getComponents().addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
            );
        };
    }


}
