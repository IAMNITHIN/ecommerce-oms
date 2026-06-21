package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Controller class to handle all incoming HTTP requests for products.
 * @RestController means this class serves REST APIs and automatically converts responses to JSON.
 * @RequestMapping defines the base URL for all endpoints in this controller (e.g., http://localhost:8082/api/products).
 * @Tag is for Swagger/OpenAPI documentation.
 */
@Tag(name = "Product Service", description = "CRUD operations for products")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     * @PostMapping maps HTTP POST requests to this method.
     * @RequestBody tells Spring to convert the incoming JSON into a ProductDto object.
     * @Valid triggers the validation rules defined in ProductDto.
     */
    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("REST request to create Product : {}", productDto.getName());
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); // Returns 201 Created
    }

    /**
     * Gets a single product by its ID.
     * @GetMapping maps HTTP GET requests. The "{id}" is a path variable (e.g., /api/products/1).
     * @PathVariable extracts the "id" from the URL.
     */
    @Operation(summary = "Get a product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        logger.info("REST request to get Product : {}", id);
        ProductDto product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK); // Returns 200 OK
    }

    /**
     * Gets a list of all products.
     */
    @Operation(summary = "Get all products")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        logger.info("REST request to get all Products");
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Updates an existing product.
     * @PutMapping maps HTTP PUT requests. Used for full updates of a resource.
     */
    @Operation(summary = "Update an existing product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id,
                                                    @Valid @RequestBody ProductDto productDto) {
        logger.info("REST request to update Product : {}", id);
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * Deletes a product by its ID.
     * @DeleteMapping maps HTTP DELETE requests.
     */
    @Operation(summary = "Delete a product")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        logger.info("REST request to delete Product : {}", id);
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
    }
}
