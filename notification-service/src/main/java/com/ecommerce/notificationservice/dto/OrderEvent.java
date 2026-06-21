package com.ecommerce.notificationservice.dto;

import java.math.BigDecimal;

/**
 * The event payload received from RabbitMQ.
 * Must exactly match the fields sent by the Order Service.
 */
public class OrderEvent {
    
    private String message;
    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;

    public OrderEvent() {
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

    @Override
    public String toString() {
        return "OrderEvent{" +
                "message='" + message + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=$" + totalAmount +
                '}';
    }
}
