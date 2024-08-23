package com.backend.beer_api_application.exceptions;

public class OrderLineNotFoundException extends RuntimeException {
    public OrderLineNotFoundException(String message) {
        super(message);
    }
}