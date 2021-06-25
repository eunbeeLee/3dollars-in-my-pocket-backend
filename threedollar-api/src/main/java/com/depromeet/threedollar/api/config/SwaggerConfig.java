package com.depromeet.threedollar.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components().addSecuritySchemes("Authorization", new SecurityScheme()
				.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
				.in(SecurityScheme.In.HEADER).name("Authorization")))
				.info(new Info()
						.title("Muyaho API Server")
						.description("Muyaho API Docs"));
	}

}