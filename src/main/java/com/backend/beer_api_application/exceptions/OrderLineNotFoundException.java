package com.backend.beer_api_application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderLineNotFoundException extends RuntimeException {
    public OrderLineNotFoundException(String message) {super(message);
   }
}