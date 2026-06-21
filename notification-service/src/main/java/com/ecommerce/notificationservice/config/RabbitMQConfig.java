package com.ecommerce.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration for the Notification Service (Consumer).
 * It creates the Queue, the Exchange, and binds them together using the Routing Key.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // 1. Create the Queue where messages will sit until we read them
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    // 2. Create the Exchange (Matches the one in Order Service)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    // 3. Bind the Queue to the Exchange using the Routing Key
    // This tells RabbitMQ: "Any message sent to 'order_exchange' with the key 'order_routing_key' 
    // should be routed into the 'order_queue'".
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public org.springframework.amqp.support.converter.MessageConverter converter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }

    @Bean
    public org.springframework.amqp.core.AmqpTemplate amqpTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate = new org.springframework.amqp.rabbit.core.RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
