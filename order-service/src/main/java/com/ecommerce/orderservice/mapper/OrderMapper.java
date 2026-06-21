package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface to convert between Order Entities and DTOs.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto mapToDto(Order order);
    Order mapToEntity(OrderDto orderDto);

    OrderItemDto mapItemToDto(OrderItem orderItem);
    OrderItem mapItemToEntity(OrderItemDto orderItemDto);
}
