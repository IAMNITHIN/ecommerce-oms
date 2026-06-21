package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity.
 * Extending JpaRepository provides us with built-in methods for CRUD operations
 * (like save, findById, findAll, deleteById) without writing any SQL queries!
 *
 * <Product, Long> specifies the Entity type and the Primary Key type.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // We can add custom query methods here later if needed, e.g., List<Product> findByCategory(String category);
}
