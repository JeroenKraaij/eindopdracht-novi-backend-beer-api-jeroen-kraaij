package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public static UserOutputDto transferToUserOutputDto(User user) {
        UserOutputDto dto = new UserOutputDto();
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.isEnabled());
        dto.setApikey(user.getApikey());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setAuthorities(user.getAuthorities());
        return dto;
    }

    public static User transferToUserEntity(UserInputDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }
}

