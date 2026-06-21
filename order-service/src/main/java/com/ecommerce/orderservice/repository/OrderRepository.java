package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Custom method to fetch all orders for a specific user.
    // Spring Data JPA magically generates the SQL query: SELECT * FROM orders WHERE user_id = ?
    List<Order> findByUserId(Long userId);
}
