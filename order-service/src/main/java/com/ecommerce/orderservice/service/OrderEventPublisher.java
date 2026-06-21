package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Publisher service responsible for sending messages to RabbitMQ.
 * It listens for internal application events and forwards them to the message broker.
 */
@Service
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @TransactionalEventListener ensures that this method is ONLY called 
     * if the database transaction (saving the order) commits successfully.
     * phase = TransactionPhase.AFTER_COMMIT is the default, but we declare it explicitly for clarity.
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishOrderEvent(OrderEvent orderEvent) {
        // Send the event payload to the configured exchange with the routing key
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
        System.out.println("✅ Event successfully sent to RabbitMQ after DB commit! Order ID: " + orderEvent.getOrderId());
    }
}
