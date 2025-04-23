package br.com.powercrm.app.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    private final String queueName;
    private final String exchangeName;
    private final String routingKey;
    private final String deadLetterQueueName;
    private final String deadLetterExchange;
    private final String deadLetterRoutingKey;
    private final String retryQueueName;
    public RabbitMQConfig( @Value("${vehicle.queue.name}") final String queueName,
                           @Value("${vehicle.exchange.name}") final  String exchangeName,
                           @Value("${vehicle.routing.key}") final String routingKey,
                           @Value("${dead.letter.queue.name}") final String deadLetterQueueName,
                           @Value("${dead.letter.exchange.name}") final String deadLetterExchange,
                           @Value("${dead.letter.routing.key}") final String deadLetterRoutingKey,
                           @Value("${retry.queue.name}") final String retryQueueName
                           ){
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.deadLetterQueueName = deadLetterQueueName;
        this.deadLetterExchange = deadLetterExchange;
        this.deadLetterRoutingKey = deadLetterRoutingKey;
        this.retryQueueName = retryQueueName;
    }


    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(deadLetterQueueName).build();
    }


    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(retryQueueName)
                .withArgument("x-message-ttl", 10000)
                .withArgument("x-dead-letter-exchange", exchangeName)
                .withArgument("x-dead-letter-routing-key", routingKey)
                .build();
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(deadLetterExchange);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(deadLetterRoutingKey);
    }

    @Bean
    public Binding retryBinding(Queue retryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(retryQueue).to(exchange).with(routingKey);
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
