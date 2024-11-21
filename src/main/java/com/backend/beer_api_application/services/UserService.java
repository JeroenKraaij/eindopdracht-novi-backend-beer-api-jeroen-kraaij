package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.UserInputDto;
import com.backend.beer_api_application.dto.mapper.UserMapper;
import com.backend.beer_api_application.dto.output.UserOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Authority;
import com.backend.beer_api_application.models.User;
import com.backend.beer_api_application.repositories.UserRepository;
import com.backend.beer_api_application.utils.RandomStringGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutputDto getUser(String username) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only access your own profile.");
        }

        User user = findUserByUsername(username);
        return UserMapper.transferToUserOutputDto(user);
    }

    public List<UserOutputDto> getAllUsers(String username) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only access your own profile.");
        }

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::transferToUserOutputDto)
                .collect(Collectors.toList());
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserInputDto userInputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        User newUser = UserMapper.transferToUserEntity(userInputDto);
        newUser.setApikey(randomString);
        newUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepository.save(newUser);
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only delete your own profile.");
        }

        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException("User not found: " + username);
        }
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserOutputDto updatedUserDto, String newRawPassword) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only update your own profile.");
        }

        User existingUser = findUserByUsername(username);

        existingUser.setEnabled(updatedUserDto.getEnabled());
        existingUser.setApikey(updatedUserDto.getApikey());
        existingUser.setEmail(updatedUserDto.getEmail());

        if (newRawPassword != null && !newRawPassword.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(newRawPassword));
        }

        userRepository.save(existingUser);
    }

    public Set<Authority> getAuthorities(String username) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only access your own authorities.");
        }

        User user = findUserByUsername(username);
        return user.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only modify your own authorities.");
        }

        User user = findUserByUsername(username);
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        String currentUsername = getAuthenticatedUsername();
        if (!currentUsername.equals(username)) {
            throw new SecurityException("You can only modify your own authorities.");
        }

        User user = findUserByUsername(username);
        Authority authorityToRemove = user.getAuthorities().stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Authority not found: " + authority));

        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    User findUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}