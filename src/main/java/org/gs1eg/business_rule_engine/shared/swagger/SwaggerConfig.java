package org.gs1eg.business_rule_engine.shared.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI businessRuleEngineAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Business Rule Engine API")
                        .version("v1.0")
                        .description("API documentation for Business Rule Engine"));
    }
}
