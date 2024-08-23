package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.exceptions.RequestValidationException;
import com.backend.beer_api_application.services.UserService;
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
    public ResponseEntity<UserOutputDto> createUser(@RequestBody Map<String, Object> request) {
        try {
            String username = (String) request.get("username");
            String rawPassword = (String) request.get("password");  // Extract password from request
            String email = (String) request.get("email");

            UserOutputDto userDto = new UserOutputDto();
            userDto.setUsername(username);
            userDto.setEmail(email);
            // Set other fields as needed

            String newUsername = userService.createUser(userDto, rawPassword);
            userService.addAuthority(newUsername, "ROLE_USER");

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                    .buildAndExpand(newUsername).toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            throw new RequestValidationException("Invalid input data: " + ex.getMessage());
        }
    }

    @PutMapping(value = "/users/{username}")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable("username") String username, @RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            Boolean enabled = (Boolean) request.get("enabled");
            String newRawPassword = (String) request.get("password");  // Optional password update

            UserOutputDto userDto = new UserOutputDto();
            userDto.setEmail(email);
            userDto.setEnabled(enabled);
            // Set other fields as needed

            userService.updateUser(username, userDto, newRawPassword);

            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new RequestValidationException("Invalid input data: " + ex.getMessage());
        }
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/users/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

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

    @DeleteMapping(value = "/users/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
