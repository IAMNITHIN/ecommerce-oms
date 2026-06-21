package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.AuthResponseDto;
import com.ecommerce.userservice.dto.UserLoginDto;
import com.ecommerce.userservice.dto.UserRegistrationDto;
import com.ecommerce.userservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        logger.info("Received request to register user with username: {}", registrationDto.getUsername());
        AuthResponseDto responseDto = authService.registerUser(registrationDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody UserLoginDto loginDto) {
        logger.info("Received login request for username: {}", loginDto.getUsername());
        AuthResponseDto responseDto = authService.loginUser(loginDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
