package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.AuthResponseDto;
import com.ecommerce.userservice.dto.UserRegistrationDto;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.UserAlreadyExistsException;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test class for AuthServiceImpl using JUnit 5 and Mockito.
 * @ExtendWith(MockitoExtension.class) enables Mockito for this class.
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    // @Mock creates fake "dummy" versions of our dependencies so we don't need a real DB or Security setup
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    // @InjectMocks creates our real Service, and injects all the @Mock dummies into it automatically
    @InjectMocks
    private AuthServiceImpl authService;

    private UserRegistrationDto mockRegistrationDto;
    private User mockUser;
    @Mock
    private Authentication mockAuthentication;

    @BeforeEach
    void setUp() {
        // Setup dummy data before each test
        mockRegistrationDto = new UserRegistrationDto();
        mockRegistrationDto.setUsername("testuser");
        mockRegistrationDto.setEmail("test@example.com");
        mockRegistrationDto.setPassword("password123");

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");
    }

    @Test
    void testRegisterUser_Success() {
        // 1. Arrange: Tell the dummy dependencies what to return
        when(userRepository.existsByUsername("testuser")).thenReturn(false); // Username is available
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false); // Email is available
        when(userMapper.toEntity(mockRegistrationDto)).thenReturn(mockUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(jwtTokenProvider.generateToken(mockAuthentication)).thenReturn("fake-jwt-token");

        // 2. Act: Call the real service method
        AuthResponseDto result = authService.registerUser(mockRegistrationDto);

        // 3. Assert: Verify the result is exactly what we expect
        assertNotNull(result);
        assertEquals("User registered successfully!", result.getMessage());
        assertEquals("testuser", result.getUsername());
        assertEquals("fake-jwt-token", result.getAccessToken());
        
        // Verify that the repository's save method was called exactly once
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        // 1. Arrange: Tell the dummy repository that the username is already taken!
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // 2. Act & Assert: Verify that calling the service throws our custom exception
        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.registerUser(mockRegistrationDto);
        });
        
        // Verify that save() was NEVER called because it failed early
        verify(userRepository, never()).save(any(User.class));
    }
}
