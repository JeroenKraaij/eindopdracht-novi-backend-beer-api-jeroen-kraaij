package com.backend.beer_api_application.dto.input;

import lombok.Data;

@Data
public class UserInputDto {
    private String username;
    private String password;
    private String email;
}