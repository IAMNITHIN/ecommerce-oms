package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductService.
 * @Service marks this class as a Spring Bean containing business logic.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Constructor-based dependency injection.
     * Spring automatically provides the instances of Repository and Mapper when creating this Service.
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        // 1. Convert DTO (data from user) to Entity (data for database)
        Product product = productMapper.mapToEntity(productDto);
        
        // 2. Save Entity to the database using Repository
        Product savedProduct = productRepository.save(product);
        logger.info("Successfully created product with id: {}", savedProduct.getId());
        
        // 3. Convert the saved Entity back to DTO and return to the user
        return productMapper.mapToDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long id) {
        // Find product by ID, if not found throw our custom exception
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product with id {} not found", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });
                
        return productMapper.mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        // Fetch all entities from database
        List<Product> products = productRepository.findAll();
        
        // Convert list of Entities to list of DTOs using Java Streams
        return products.stream()
                .map((product) -> productMapper.mapToDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        // 1. Check if the product exists in the database
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to update: Product with id {} not found", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });
                
        // 2. Update the existing entity with new data
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setCategory(productDto.getCategory());
        
        // 3. Save the updated entity
        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Successfully updated product with id: {}", updatedProduct.getId());
        
        // 4. Return as DTO
        return productMapper.mapToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        // 1. Check if the product exists before deleting
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Failed to delete: Product with id {} not found", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });
                
        // 2. Delete it
        productRepository.delete(existingProduct);
        logger.info("Successfully deleted product with id: {}", id);
    }
}
