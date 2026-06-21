package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    
    OrderDto createOrder(OrderDto orderDto);
    
    OrderDto getOrderById(Long id);
    
    List<OrderDto> getOrdersByUserId(Long userId);
    
    OrderDto updateOrderStatus(Long id, OrderStatus status);
}
