package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.UserRegistrationDto;
import com.ecommerce.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRegistrationDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        // Password is not mapped here, it's encoded separately
        
        return user;
    }
}
