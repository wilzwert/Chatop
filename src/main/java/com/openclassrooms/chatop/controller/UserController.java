package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.*;
import com.openclassrooms.chatop.mapper.UserMapper;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;


/**
 * @author Wilhelm Zwertvaegher
 * Rest controller for auth and user related endpoints
 */
@RestController
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "User", description = "User operations")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
        })
})
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(final UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Register new user", description = "Register new user")
    @PostMapping("/register")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration succeeded", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtTokenDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "Email already exists", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    public JwtTokenDto register(@Valid @RequestBody final RegisterUserDto registerUserDto) {
        log.info("Register user with email {}", registerUserDto.getEmail());
        User registerUser = userMapper.registerUserDtoToUser(registerUserDto);
        try {
            User user = userService.registerUser(registerUser);
            String token = userService.generateToken(user);
            log.info("User with email {} registered with id {}", registerUserDto.getEmail(), user.getId());
            return new JwtTokenDto(token);
        }
        catch (EntityExistsException e) {
            log.warn("Email {} already exists", registerUserDto.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @Operation(summary = "Login", description = "Get an access token")
    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login succeeded", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtTokenDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Login failed", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    public JwtTokenDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("User login with email {}", loginRequestDto.getEmail());
        try {
            User user = userService.authenticateUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            String token = userService.generateToken(user);
            log.info("User with email {} successfully authenticated, sending JWT token", loginRequestDto.getEmail());
            return new JwtTokenDto(token);
        }
        catch (AuthenticationException e) {
            log.info("Login failed for User with email {}", loginRequestDto.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed."+e.getMessage());
        }
    }


    @Operation(summary = "Get user info", description = "Get user info")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    public UserDto getUserInfo(Principal principal) {
        log.info("Get current User info, email is {}", principal.getName());
        Optional<User> foundUser = userService.findUserByEmail(principal.getName());
        if(foundUser.isEmpty()) {
            log.warn("No User found for email {}", principal.getName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        log.info("User found for email {}, sending DTO", foundUser.get().getEmail());
        return userMapper.userToUserDTO(foundUser.get());
    }


    @Operation(summary = "Get user info by its id", description = "Get user info by its id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    public UserDto getUser(@PathVariable long id) {
        log.info("Get User by id {}", id);
        Optional<User> foundUser = userService.findUserById(id);
        if(foundUser.isEmpty()) {
            log.warn("No User found for id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        log.info("User found with id {}, sending DTO", foundUser.get().getId());
        return userMapper.userToUserDTO(foundUser.get());
    }


    @Operation(summary = "Delete current user info", description = "Delete current user info")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(Principal principal) {
        log.info("Delete current User, email is {}", principal.getName());
        Optional<User> foundUser = userService.findUserByEmail(principal.getName());
        if(foundUser.isEmpty()) {
            log.warn("Delete current User failed: no user found for email {}", principal.getName());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteUser(foundUser.get());
        log.info("Current User deleted with email {}", foundUser.get().getEmail());
    }
}