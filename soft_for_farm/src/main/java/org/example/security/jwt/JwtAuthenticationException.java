package org.example.security.jwt;

public class JwtAuthenticationException extends Throwable {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
