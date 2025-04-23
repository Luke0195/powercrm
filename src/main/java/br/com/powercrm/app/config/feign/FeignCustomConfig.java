package br.com.powercrm.app.config.feign;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCustomConfig {

    @Bean
    public Retryer neverRetry(){
        return Retryer.NEVER_RETRY;
    }
}
