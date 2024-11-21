package com.backend.beer_api_application.exceptions;

public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}
