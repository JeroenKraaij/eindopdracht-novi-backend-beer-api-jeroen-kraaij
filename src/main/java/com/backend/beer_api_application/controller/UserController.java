package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users/me")
    public ResponseEntity<?> getMyUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            UserOutputDto userDto = userService.getUser(currentUsername);
            return ResponseEntity.ok().body(userDto);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + currentUsername);
        }
    }

    @PutMapping(value = "/users/me")
    public ResponseEntity<?> updateMyUserProfile(@RequestBody Map<String, Object> request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        try {
            String email = (String) request.get("email");
            Boolean enabled = (Boolean) request.get("enabled");
            String newRawPassword = (String) request.get("password");

            UserOutputDto userDto = new UserOutputDto();
            userDto.setEmail(email);
            userDto.setEnabled(enabled);

            userService.updateUser(currentUsername, userDto, newRawPassword);
            return ResponseEntity.noContent().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + currentUsername);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/users/me")
    public ResponseEntity<?> deleteMyUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (!userService.userExists(currentUsername)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + currentUsername);
        }
        userService.deleteUser(currentUsername);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/users/me/authorities")
    public ResponseEntity<?> getMyUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            return ResponseEntity.ok().body(userService.getAuthorities(currentUsername));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + currentUsername);
        }
    }

    @PostMapping(value = "/users/me/authorities")
    public ResponseEntity<?> addMyUserAuthority(@RequestBody Map<String, Object> fields) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(currentUsername, authorityName);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/users/me/authorities/{authority}")
    public ResponseEntity<?> deleteMyUserAuthority(@PathVariable("authority") String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        try {
            userService.removeAuthority(currentUsername, authority);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/users/")
    public ResponseEntity<?> getAllUsers() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            return ResponseEntity.ok().body(userService.getAllUsers(currentUsername));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        try {
            String newUsername = userService.createUser(userInputDto);
            userService.addAuthority(newUsername, "ROLE_USER");
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                    .buildAndExpand(newUsername).toUri();
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data: " + e.getMessage());
        }
    }
}