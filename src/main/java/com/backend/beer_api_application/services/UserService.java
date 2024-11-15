package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.mapper.UserMapper;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Authority;
import com.backend.beer_api_application.models.User;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.repositories.UserRepository;
import com.backend.beer_api_application.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    public List<UserOutputDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::transferToUserOutputDto)
                .collect(Collectors.toList());
    }

    public UserOutputDto getUser(String username) {
        User user = findUserByUsername(username);
        return UserMapper.transferToUserOutputDto(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
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
        newUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));  // Handle password encryption
        userRepository.save(newUser);

        return newUser.getUsername();
    }

    // Delete a user by username
    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException("User not found: " + username);
        }
        userRepository.deleteById(username);
    }

    // Update a user by username
    public void updateUser(String username, UserOutputDto updatedUserDto, String newRawPassword) {
        User existingUser = findUserByUsername(username);  // Reuse method to find user

        // Update user fields
        existingUser.setEnabled(updatedUserDto.getEnabled());
        existingUser.setApikey(updatedUserDto.getApikey());
        existingUser.setEmail(updatedUserDto.getEmail());

        // Optionally update the password if a new one is provided
        if (newRawPassword != null && !newRawPassword.isEmpty()) {
            existingUser.setPassword( passwordEncoder.encode(newRawPassword));
        }

        userRepository.save(existingUser);
    }

    // Get authorities of a user
    public Set<Authority> getAuthorities(String username) {
        User user = findUserByUsername(username);
        return user.getAuthorities();
    }

    // Add an authority to a user
    public void addAuthority(String username, String authority) {
        User user = findUserByUsername(username);

        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    // Remove an authority from a user
    public void removeAuthority(String username, String authority) {
        User user = findUserByUsername(username);

        Authority authorityToRemove = user.getAuthorities().stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Authority not found: " + authority));

        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }
}
