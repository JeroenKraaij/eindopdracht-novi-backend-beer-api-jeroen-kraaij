package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.exceptions.RequestValidationException;
import com.backend.beer_api_application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        List<UserOutputDto> userOutputDtos = userService.getUsers();
        return ResponseEntity.ok().body(userOutputDtos);
    }

    @GetMapping(value = "/users/{username}")
    public ResponseEntity<UserOutputDto> getUserByName(@PathVariable("username") String username) {
        UserOutputDto userDto = userService.getUser(username);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        try {

            String newUsername = userService.createUser(userInputDto);
            userService.addAuthority(newUsername, "ROLE_USER");

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                    .buildAndExpand(newUsername).toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            throw new RequestValidationException("Invalid input data: " + ex.getMessage());
        }
    }

    // Update an existing user by username
    @PutMapping(value = "/users/{username}")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable("username") String username, @RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            Boolean enabled = (Boolean) request.get("enabled");
            String newRawPassword = (String) request.get("password");

            UserOutputDto userDto = new UserOutputDto();
            userDto.setEmail(email);
            userDto.setEnabled(enabled);

            userService.updateUser(username, userDto, newRawPassword);

            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new RequestValidationException("Invalid input data: " + ex.getMessage());
        }
    }

    // Delete a user by username
    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "/users/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    // Add an authority to a user
    @PostMapping(value = "/users/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new RequestValidationException(ex.getMessage());
        }
    }

    //
    @DeleteMapping(value = "/users/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
