package br.com.powercrm.app.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

   @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("POWERCRM API")
                        .version("1.1.0")
                        .description("API de usuário e veículos da POWERCRM"))
                .addTagsItem(new Tag().name("Usuários").description("Gerenciar Usuários"))
                .addTagsItem(new Tag().name("Veículos").description("Gerenciar Veículos"));
    }
}
