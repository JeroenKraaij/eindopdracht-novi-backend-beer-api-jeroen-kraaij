package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.mapper.UserMapper;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.models.Authority;
import com.backend.beer_api_application.models.User;
import com.backend.beer_api_application.repositories.UserRepository;
import com.backend.beer_api_application.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Find a user by username
    public User findUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // Retrieve user details by username
    public Optional<UserOutputDto> getUser(String username) {
        return userRepository.findById(username)
                .map(UserMapper::transferToUserOutputDto);
    }

    // Retrieve all users visible to Admin
    public List<UserOutputDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::transferToUserOutputDto)
                .collect(Collectors.toList());
    }

    // Check if a user exists
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    // Create a new user
    public String createUser(UserInputDto userInputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        User newUser = UserMapper.transferToUserEntity(userInputDto);
        newUser.setApikey(randomString);
        newUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepository.save(newUser);
        return newUser.getUsername();
    }

    // Delete a user by username
    public boolean deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            return true;
        }
        return false;
    }

    // Delete a user by username (specific for DeleteMapping)
    public boolean deleteUserByUsername(String username) {
        return deleteUser(username);
    }

    // Update user details
    public boolean updateUser(String username, UserOutputDto updatedUserDto, String newRawPassword) {
        Optional<User> userOpt = userRepository.findById(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEnabled(updatedUserDto.getEnabled());
            user.setApikey(updatedUserDto.getApikey());
            user.setEmail(updatedUserDto.getEmail());

            if (newRawPassword != null && !newRawPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(newRawPassword));
            }

            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Update or add a username
    public boolean updateUsername(String username, UserInputDto userInputDto) {
        Optional<User> userOpt = userRepository.findById(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(userInputDto.getEmail());
            user.setEnabled(userInputDto.getEnabled());

            if (userInputDto.getPassword() != null && !userInputDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
            }

            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Update user profile with request fields
    public boolean updateUserProfile(String username, Map<String, Object> request) {
        Optional<User> userOpt = userRepository.findById(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Extract and update fields from request
            String email = (String) request.get("email");
            String apiKey = (String) request.get("apikey");
            Boolean enabled = (Boolean) request.get("enabled");
            String newRawPassword = (String) request.get("password");

            if (email != null) {
                user.setEmail(email);
            }

            if (apiKey != null) {
                user.setApikey(apiKey);
            }

            if (enabled != null) {
                user.setEnabled(enabled);
            }

            if (newRawPassword != null && !newRawPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(newRawPassword));
            }

            userRepository.save(user);
            return true;
        }

        return false;
    }

    // Retrieve authorities of a user
    public Optional<Set<Authority>> getAuthorities(String username) {
        return userRepository.findById(username)
                .map(User::getAuthorities);
    }

    // Add an authority to a user
    public boolean addAuthority(String username, String authority) {
        Optional<User> userOpt = userRepository.findById(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.addAuthority(new Authority(username, authority));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Remove an authority from a user
    public boolean removeAuthority(String username, String authority) {
        Optional<User> userOpt = userRepository.findById(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Authority authorityToRemove = user.getAuthorities().stream()
                    .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                    .findFirst()
                    .orElse(null);

            if (authorityToRemove != null) {
                user.removeAuthority(authorityToRemove);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
