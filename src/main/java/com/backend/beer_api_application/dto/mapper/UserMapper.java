package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.models.User;
import org.springframework.stereotype.Service;

@Service
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
    public static User toEntity(UserInputDto dto) {
        User user = new User();
        user.setUsername(dto.getUserName());
        user.setEmail(dto.getEmail());
        return user;
    }
}

