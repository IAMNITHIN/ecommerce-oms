package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.AuthResponseDto;
import com.ecommerce.userservice.dto.UserLoginDto;
import com.ecommerce.userservice.dto.UserRegistrationDto;

public interface AuthService {
    AuthResponseDto registerUser(UserRegistrationDto registrationDto);
    AuthResponseDto loginUser(UserLoginDto loginDto);
}
