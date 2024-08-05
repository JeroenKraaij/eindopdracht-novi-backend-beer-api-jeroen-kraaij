package com.backend.beer_api_application.controllers;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
