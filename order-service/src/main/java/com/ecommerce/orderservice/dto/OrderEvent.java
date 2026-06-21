package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;

/**
 * The event payload that will be sent to RabbitMQ.
 */
public class OrderEvent {
    
    private String message;
    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;

    public OrderEvent() {
    }

    public OrderEvent(String message, Long orderId, Long userId, BigDecimal totalAmount) {
        this.message = message;
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
