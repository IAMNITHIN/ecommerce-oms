package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;

/**
 * A DTO representing the response we expect when calling the Product Service via Feign.
 * We only include the fields we care about (like id, price, quantity).
 */
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity; // This represents the stock available

    public ProductDto() {
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
}
