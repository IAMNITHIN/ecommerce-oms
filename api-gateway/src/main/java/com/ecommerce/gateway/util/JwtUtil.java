package com.ecommerce.gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;

/**
 * Utility class to validate JSON Web Tokens (JWT).
 * This class is kept simple and beginner-friendly.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Validate the given JWT token string.
     * If the token is invalid, it throws an exception.
     * 
     * @param token the JWT token to validate
     */
    public void validateToken(final String token) {
        // Parse the token using the secret key to ensure it's valid and hasn't been tampered with
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    /**
     * Get the signing key from the secret string.
     * 
     * @return the cryptographic Key object
     */
    private Key getSignKey() {
        // User-service uses getBytes() instead of Base64 decoding, so we must match it here
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
