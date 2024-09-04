package com.backend.beer_api_application.dto.output;

import lombok.Data;

@Data
public class AuthenticationOutputDto {
    private final String token;

    public AuthenticationOutputDto(String token) {
        this.token = token;
    }
}
