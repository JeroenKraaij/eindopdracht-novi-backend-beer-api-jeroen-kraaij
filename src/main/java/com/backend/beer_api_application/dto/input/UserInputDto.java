package com.backend.beer_api_application.dto.input;

import lombok.Data;

@Data
public class UserInputDto {
    private String username;
    private String apikey;
    private String password;
    private String email;
    public boolean getEnabled() {
        return true;
    }
}