package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import java.util.List;

/**
 * Interface defining the operations (business logic) for Product Service.
 * Using an interface is a best practice in Java to decouple the implementation from the controller.
 */
public interface ProductService {
    
    ProductDto createProduct(ProductDto productDto);
    
    ProductDto getProductById(Long id);
    
    List<ProductDto> getAllProducts();
    
    ProductDto updateProduct(Long id, ProductDto productDto);
    
    void deleteProduct(Long id);
}
