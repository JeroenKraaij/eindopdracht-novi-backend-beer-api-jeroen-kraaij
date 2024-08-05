package com.backend.beer_api_application.controllers;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age,
        String phone
) {
}
