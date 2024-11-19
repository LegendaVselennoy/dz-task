package com.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@OpenAPIDefinition(
        info = @Info(
                title = "OpenAPI Bank",
                description = "Task management system operation", version = "v0"
        ), security = { @SecurityRequirement(name = "BasicAuth") }
)
public class OpenApiConfig {
}