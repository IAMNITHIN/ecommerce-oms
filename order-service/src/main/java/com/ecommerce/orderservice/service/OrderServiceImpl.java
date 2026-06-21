package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.OrderEvent;
import com.ecommerce.orderservice.dto.ProductDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.entity.OrderStatus;
import com.ecommerce.orderservice.exception.OutOfStockException;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    // We inject the Feign Client here so we can talk to the Product Service
    private final ProductClient productClient;
    // ApplicationEventPublisher to publish internal events
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductClient productClient, ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productClient = productClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Creates a new order.
     * @Transactional ensures that if any part of this process fails (e.g. database error),
     * everything is rolled back and no partial data is saved.
     */
    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Loop through each item the user wants to order
        for (OrderItemDto itemDto : orderDto.getOrderItems()) {
            // 1. Fetch real product details from the Product Service using OpenFeign!
            ProductDto product = productClient.getProductById(itemDto.getProductId());
            
            // 2. Validate availability
            if (product.getQuantity() < itemDto.getQuantity()) {
                logger.warn("Order failed: Product {} out of stock", product.getName());
                throw new OutOfStockException("Product " + product.getName() + " does not have enough stock.");
            }
            
            // 3. Create the OrderItem entity
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice()); // Save the current price!
            
            // 4. Add to the Order (this also sets the relationship)
            order.addOrderItem(orderItem);
            
            // 5. Calculate total
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);
        
        // Save the Order (OrderItems are saved automatically because of CascadeType.ALL)
        Order savedOrder = orderRepository.save(order);
        
        // 6. Send Event to RabbitMQ!
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setMessage("Order created successfully!");
        orderEvent.setOrderId(savedOrder.getId());
        orderEvent.setUserId(savedOrder.getUserId());
        orderEvent.setTotalAmount(savedOrder.getTotalAmount());
        
        // Publish the event internally!
        // The @TransactionalEventListener in OrderEventPublisher will catch this
        // and send it to RabbitMQ ONLY IF the database commit is successful.
        applicationEventPublisher.publishEvent(orderEvent);
        
        logger.info("Successfully created order with id: {} for user: {}", savedOrder.getId(), savedOrder.getUserId());
        return orderMapper.mapToDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Order with id {} not found", id);
                    return new ResourceNotFoundException("Order", "id", id);
                });
        return orderMapper.mapToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map((order) -> orderMapper.mapToDto(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to update status: Order with id {} not found", id);
                    return new ResourceNotFoundException("Order", "id", id);
                });
                
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        
        logger.info("Successfully updated order {} status to {}", id, status);
        return orderMapper.mapToDto(updatedOrder);
    }
}
