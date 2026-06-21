package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface to convert between Entity and DTO.
 * MapStruct automatically generates the implementation class for this interface during the build process.
 * We use 'spring' componentModel so we can @Autowired this mapper in our services.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    // Converts Entity to DTO
    ProductDto mapToDto(Product product);

    // Converts DTO to Entity
    Product mapToEntity(ProductDto productDto);
}
