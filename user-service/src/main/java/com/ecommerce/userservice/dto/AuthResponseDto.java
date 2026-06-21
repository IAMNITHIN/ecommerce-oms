package com.ecommerce.userservice.dto;

public class AuthResponseDto {
    private String message;
    private String username;
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseDto(String message, String username, String accessToken) {
        this.message = message;
        this.username = username;
        this.accessToken = accessToken;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
