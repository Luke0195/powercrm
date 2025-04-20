package br.com.powercrm.app.config.feign;

import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCustomConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJkMzRlZjgxZi0wMmQ5LTQ0N2MtOWViZC0wOWU5ZTVhMmMyNDQiLCJlbWFpbCI6Imx1Y2FzMjAxMjczQGdtYWlsLmNvbSIsImlhdCI6MTc0NTE3NDQwNn0.3u0sLbhaAEs0e-UNF6d5OxZITobEYOg5F-P-zKdUNBY"; // Pode ser fixo ou buscar de algum serviço
            requestTemplate.header("Authorization", "Bearer " + token);
            System.out.println("🔐 Interceptando requisição: " + requestTemplate);
        };
    }


    @Bean
    public Retryer neverRetry(){
        return Retryer.NEVER_RETRY;
    }
}
