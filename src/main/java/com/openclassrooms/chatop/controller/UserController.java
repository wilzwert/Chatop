package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.JwtTokenDto;
import com.openclassrooms.chatop.dto.LoginRequestDto;
import com.openclassrooms.chatop.dto.RegisterUserDto;
import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.mapper.UserMapper;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User", description = "User operations")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserController(@Autowired final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register new user", description = "Register new user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public JwtTokenDto register(@Valid @RequestBody final RegisterUserDto registerUserDto) {
        User registerUser = userMapper.registerUserDtoToUser(registerUserDto);
        registerUser.setCreatedAt(LocalDateTime.now());
        registerUser.setUpdatedAt(LocalDateTime.now());
        try {
            User user = userService.registerUser(registerUser);
        }
        catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        return new JwtTokenDto("jwt");
    }

    @Operation(summary = "Login", description = "Get an access token")
    @PostMapping("/login")
    public JwtTokenDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        try {
            User user = userService.authenticateUser(loginRequestDto.getLogin(), loginRequestDto.getPassword());
            String token = userService.generateToken(user);
            return new JwtTokenDto(token);
        }
        catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed.");
        }
    }


    @Operation(summary = "Get user info", description = "Get user info")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public UserDto getUserInfo(Principal principal) {
        Optional<User> foundUser = userService.findUserByEmail(principal.getName());
        if(foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.userToUserDTO(foundUser.get());
    }


    @Operation(summary = "Get user info by its id", description = "Get user info by its id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable long id) {
        Optional<User> foundUser = userService.findUserById(id);
        if(foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.userToUserDTO(foundUser.get());
    }
}
