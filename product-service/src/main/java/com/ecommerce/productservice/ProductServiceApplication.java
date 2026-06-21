package com.ecommerce.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class to bootstrap the Product Service.
 * @SpringBootApplication marks this as a Spring Boot application, enabling auto-configuration and component scanning.
 * @EnableDiscoveryClient allows this service to register with the Eureka Discovery Server.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        // Starts the Spring application context
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
