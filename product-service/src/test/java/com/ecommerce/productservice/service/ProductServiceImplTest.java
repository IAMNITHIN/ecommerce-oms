package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test class for ProductServiceImpl using JUnit 5 and Mockito.
 * @ExtendWith(MockitoExtension.class) initializes Mockito annotations.
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    // @Mock creates a fake "dummy" version of our database repository
    @Mock
    private ProductRepository productRepository;

    // @Mock creates a dummy version of our mapper
    @Mock
    private ProductMapper productMapper;

    // @InjectMocks creates our real Service, and injects the dummy Repository and Mapper into it
    @InjectMocks
    private ProductServiceImpl productService;

    private Product mockProduct;
    private ProductDto mockProductDto;

    @BeforeEach
    void setUp() {
        // Setup dummy data before each test
        mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Test Laptop");
        mockProduct.setPrice(new BigDecimal("1000.00"));
        
        mockProductDto = new ProductDto();
        mockProductDto.setId(1L);
        mockProductDto.setName("Test Laptop");
        mockProductDto.setPrice(new BigDecimal("1000.00"));
    }

    @Test
    void testGetProductById_Success() {
        // 1. Arrange: Tell the dummy repository what to return when asked for ID 1
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(productMapper.mapToDto(mockProduct)).thenReturn(mockProductDto);

        // 2. Act: Call the real service method
        ProductDto result = productService.getProductById(1L);

        // 3. Assert: Verify the result is what we expect
        assertNotNull(result);
        assertEquals("Test Laptop", result.getName());
        assertEquals(new BigDecimal("1000.00"), result.getPrice());
        
        // Verify the database was called exactly once
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        // 1. Arrange: Tell the dummy repository to return empty for ID 99
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. Act & Assert: Verify that calling the service throws our custom exception
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(99L);
        });
        
        // Verify the database was called
        verify(productRepository, times(1)).findById(99L);
    }
}
