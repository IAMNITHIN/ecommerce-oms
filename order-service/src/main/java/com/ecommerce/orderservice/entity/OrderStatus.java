package com.ecommerce.orderservice.entity;

/**
 * Enum defining the possible states an Order can be in.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
