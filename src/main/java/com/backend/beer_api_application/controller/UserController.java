package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<?> getUserByName(@PathVariable("username") String username) {
        try {
            UserOutputDto userDto = userService.getUser(username);
            return ResponseEntity.ok().body(userDto);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + username);
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

    @PutMapping(value = "/users/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            Boolean enabled = (Boolean) request.get("enabled");
            String newRawPassword = (String) request.get("password");

            UserOutputDto userDto = new UserOutputDto();
            userDto.setEmail(email);
            userDto.setEnabled(enabled);

            userService.updateUser(username, userDto, newRawPassword);
            return ResponseEntity.noContent().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + username);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        if (!userService.userExists(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + username);
        }
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/users/{username}/authorities")
    public ResponseEntity<?> getUserAuthorities(@PathVariable("username") String username) {
        try {
            return ResponseEntity.ok().body(userService.getAuthorities(username));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + username);
        }
    }

    @PostMapping(value = "/users/{username}/authorities")
    public ResponseEntity<?> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/users/{username}/authorities/{authority}")
    public ResponseEntity<?> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        try {
            userService.removeAuthority(username, authority);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
