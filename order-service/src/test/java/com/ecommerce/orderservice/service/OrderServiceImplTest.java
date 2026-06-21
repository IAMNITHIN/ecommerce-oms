package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.ProductDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderStatus;
import com.ecommerce.orderservice.exception.OutOfStockException;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test class for OrderServiceImpl using JUnit 5 and Mockito.
 * @ExtendWith(MockitoExtension.class) initializes Mockito annotations.
 */
@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    // @Mock creates fake "dummy" versions of our dependencies
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ProductClient productClient;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    // @InjectMocks creates our real Service, and injects the dummy Repository, Client, and Mapper into it
    @InjectMocks
    private OrderServiceImpl orderService;

    private Order mockOrder;
    private OrderDto mockOrderDto;

    @BeforeEach
    void setUp() {
        // Setup dummy data before each test
        mockOrder = new Order();
        mockOrder.setId(10L);
        mockOrder.setUserId(5L);
        mockOrder.setStatus(OrderStatus.PENDING);
        
        mockOrderDto = new OrderDto();
        mockOrderDto.setId(10L);
        mockOrderDto.setUserId(5L);
    }

    @Test
    void testGetOrderById_Success() {
        // 1. Arrange: Tell the dummy repository what to return when asked for ID 10
        when(orderRepository.findById(10L)).thenReturn(Optional.of(mockOrder));
        when(orderMapper.mapToDto(mockOrder)).thenReturn(mockOrderDto);

        // 2. Act: Call the real service method
        OrderDto result = orderService.getOrderById(10L);

        // 3. Assert: Verify the result is what we expect
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(5L, result.getUserId());
        
        // Verify the database was called exactly once
        verify(orderRepository, times(1)).findById(10L);
    }

    @Test
    void testCreateOrder_OutOfStock() {
        // Setup an order request for 5 laptops
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setProductId(1L);
        itemDto.setQuantity(5);
        
        OrderDto requestDto = new OrderDto();
        requestDto.setUserId(5L);
        requestDto.setOrderItems(List.of(itemDto));

        // Create a dummy product that only has 2 in stock!
        ProductDto dummyProduct = new ProductDto();
        dummyProduct.setId(1L);
        dummyProduct.setName("Laptop");
        dummyProduct.setQuantity(2); // Only 2 left!

        // 1. Arrange: Tell the fake ProductClient to return the dummy product
        when(productClient.getProductById(1L)).thenReturn(dummyProduct);

        // 2. Act & Assert: Verify that it throws OutOfStockException because we ordered 5 but only 2 are available
        assertThrows(OutOfStockException.class, () -> {
            orderService.createOrder(requestDto);
        });
        
        // Verify that we never tried to save this invalid order to the database
        verify(orderRepository, never()).save(any(Order.class));
    }
}
