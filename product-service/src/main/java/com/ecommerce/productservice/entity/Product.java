package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity class representing a Product in the database.
 * @Entity tells Hibernate to create a table out of this class.
 * @Table specifies the name of the table in PostgreSQL.
 */
@Entity
@Table(name = "products")
public class Product {

    /**
     * @Id marks this field as the primary key.
     * @GeneratedValue configures the way of incrementing the specified column(field).
     * IDENTITY means PostgreSQL will auto-increment this ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column specifies column properties. nullable = false means this cannot be null in DB.
    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    private String category;

    // Constructors, Getters, and Setters below

    public Product() {
        // Default constructor required by JPA
    }

    public Product(String name, String description, BigDecimal price, Integer quantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
