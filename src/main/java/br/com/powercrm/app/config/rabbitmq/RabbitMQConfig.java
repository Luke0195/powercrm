package br.com.powercrm.app.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    // Fila principal
    public static final String QUEUE_NAME = "vehicle_creation_queue";
    public static final String EXCHANGE_NAME = "vehicle_exchange";
    public static final String ROUTING_KEY = "vehicle_routing_key";

    // Dead letter queue (DLQ)
    public static final String DEAD_LETTER_QUEUE_NAME = "vehicle_creation_dlq";
    public static final String DEAD_LETTER_EXCHANGE = "vehicle_creation_dlx_exchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "vehicle_dlq_routing_key";

    // Retry queue
    public static final String RETRY_QUEUE_NAME = "vehicle_creation_retry_queue";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY) // Direciona corretamente pra DLQ
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(RETRY_QUEUE_NAME)
                .withArgument("x-message-ttl", 10000) // 10 segundos de delay
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME) // Após o tempo, volta pra fila principal
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY);
    }

    @Bean
    public Binding retryBinding(Queue retryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(retryQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

}
