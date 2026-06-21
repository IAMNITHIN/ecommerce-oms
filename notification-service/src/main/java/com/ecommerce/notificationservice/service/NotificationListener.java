package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Service that constantly listens to the RabbitMQ queue for new messages.
 */
@Service
public class NotificationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);

    /**
     * @RabbitListener tells Spring to run this method automatically whenever a message
     * arrives in the specified queue ("order_queue").
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeOrderEvent(OrderEvent event) {
        
        // In a real application, we would use JavaMailSender here to send an actual email to the user.
        // For now, we simulate it with a neat console log!
        
        LOGGER.info("====================================================");
        LOGGER.info("📧 NEW EMAIL NOTIFICATION PREPARING...");
        LOGGER.info("====================================================");
        LOGGER.info("To: user_{}@example.com", event.getUserId());
        LOGGER.info("Subject: Order Confirmation - Order #{}", event.getOrderId());
        LOGGER.info("Body:");
        LOGGER.info("   Hello!");
        LOGGER.info("   We have received your order successfully.");
        LOGGER.info("   {}", event.getMessage());
        LOGGER.info("   Your total is: ${}", event.getTotalAmount());
        LOGGER.info("   Thank you for shopping with us!");
        LOGGER.info("====================================================");
        LOGGER.info("✅ EMAIL SENT SUCCESSFULLY!");
    }
}
