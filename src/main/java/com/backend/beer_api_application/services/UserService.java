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

    public User findUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public Optional<UserOutputDto> getUser(String username) {
        return userRepository.findById(username)
                .map(UserMapper::transferToUserOutputDto);
    }

    public List<UserOutputDto> getAllUsers() {
        return userRepository.findAll().stream()
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

    public boolean deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            return true;
        }
        return false;
    }

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

    public Optional<Set<Authority>> getAuthorities(String username) {
        return userRepository.findById(username)
                .map(User::getAuthorities);
    }

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
