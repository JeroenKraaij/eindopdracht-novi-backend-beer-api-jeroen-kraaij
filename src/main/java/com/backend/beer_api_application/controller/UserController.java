package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Authority;
import com.backend.beer_api_application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private String getAuthenticatedUsername() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping(value = "/users/me")
    public ResponseEntity<UserOutputDto> getMyUserProfile() {
        String currentUsername = getAuthenticatedUsername();
        return userService.getUser(currentUsername)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + currentUsername));
    }

    @PutMapping(value = "/users/me")
    public ResponseEntity<Void> updateMyUserProfile(@RequestBody Map<String, Object> request) {
        String currentUsername = getAuthenticatedUsername();

        String email = (String) request.get("email");
        String ApiKey = (String) request.get("apikey");
        Boolean enabled = (Boolean) request.get("enabled");
        String newRawPassword = (String) request.get("password");

        UserOutputDto userDto = new UserOutputDto();
        userDto.setApikey(ApiKey);
        userDto.setEmail(email);
        userDto.setEnabled(enabled);

        boolean updated = userService.updateUser(currentUsername, userDto, newRawPassword);
        if (!updated) {
            throw new RecordNotFoundException("User not found: " + currentUsername);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/users/me")
    public ResponseEntity<Void> deleteMyUserProfile() {
        String currentUsername = getAuthenticatedUsername();

        boolean deleted = userService.deleteUser(currentUsername);
        if (!deleted) {
            throw new RecordNotFoundException("User not found: " + currentUsername);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/users/me/authorities")
    public ResponseEntity<Set<Authority>> getMyUserAuthorities() {
        String currentUsername = getAuthenticatedUsername();
        return userService.getAuthorities(currentUsername)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + currentUsername));
    }

    @PostMapping(value = "/users/me/authorities")
    public ResponseEntity<Void> addMyUserAuthority(@RequestBody Map<String, Object> fields) {
        String currentUsername = getAuthenticatedUsername();
        String authorityName = (String) fields.get("authority");

        boolean added = userService.addAuthority(currentUsername, authorityName);
        if (!added) {
            throw new RecordNotFoundException("User not found: " + currentUsername);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/users/me/authorities/{authority}")
    public ResponseEntity<Void> deleteMyUserAuthority(@PathVariable("authority") String authority) {
        String currentUsername = getAuthenticatedUsername();

        boolean removed = userService.removeAuthority(currentUsername, authority);
        if (!removed) {
            throw new RecordNotFoundException("User or authority not found for user: " + currentUsername);
        }

        return ResponseEntity.noContent().build();
    }

    // User management endpoints by Admin

    @GetMapping(value = "/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        String newUsername = userService.createUser(userInputDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/users/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable String username) {
        return userService.getUser(username)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + username));
    }

    @PutMapping(value = "/users/{username}")
    public ResponseEntity<Void> addOrUpdateUsername(@PathVariable String username, @Valid @RequestBody UserInputDto userInputDto) {
        boolean updated = userService.updateUsername(username, userInputDto);
        if (!updated) {
            throw new RecordNotFoundException("User not found: " + username);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        boolean deleted = userService.deleteUserByUsername(username);
        if (!deleted) {
            throw new RecordNotFoundException("User not found: " + username);
        }
        return ResponseEntity.noContent().build();
    }
}
