package com.backend.beer_api_application.dto.output;

import com.backend.beer_api_application.models.Authority;
import lombok.Data;

import java.util.Set;

@Data
public class UserOutputDto {

    private Long id;
    private String username;
    private Boolean enabled;
    private String apikey;
    private String email;
    private Set<Authority> authorities;
}

