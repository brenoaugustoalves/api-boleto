package br.com.breno.api_boleto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPi() {
        return new OpenAPI().info(new Info()
                .title("Api Boleto")
                .description("API de pagamentos de boletos")
                .contact(new Contact().name("Breno Augusto").email("brenoaugustoalves@gmail.com"))
                .version("1.0.0")
        );
    }
}
