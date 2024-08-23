package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.models.User;

public class UserMapper {

    // Convert User entity to UserOutputDto
    public static UserOutputDto toDto(User user) {
        UserOutputDto dto = new UserOutputDto();
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.isEnabled());
        dto.setApikey(user.getApikey());
        dto.setEmail(user.getEmail());
        dto.setAuthorities(user.getAuthorities());
        return dto;
    }

    // Convert UserOutputDto to User entity
    public static User toEntity(UserOutputDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEnabled(dto.getEnabled());
        user.setApikey(dto.getApikey());
        user.setEmail(dto.getEmail());
        user.getAuthorities().addAll(dto.getAuthorities());
        return user;
    }
}

