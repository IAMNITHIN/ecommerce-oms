package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * OpenFeign Client to talk to the Product Service.
 * @FeignClient name="product-service" tells Spring to look up the "product-service" in Eureka Service Discovery.
 * We don't need to write the actual HTTP call logic; Spring implements this interface on the fly!
 */
@FeignClient(name = "product-service")
public interface ProductClient {

    /**
     * This defines the exact HTTP GET request we want to make to the Product Service.
     * It maps to the getProductById endpoint in the ProductController of the Product Service.
     */
    @GetMapping("/api/products/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);
}
