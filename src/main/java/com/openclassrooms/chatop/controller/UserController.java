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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@RestController
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

    public UserController(@Autowired final UserService userService, @Autowired UserMapper userMapper) {
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
        User registerUser = userMapper.registerUserDtoToUser(registerUserDto);
        try {
            User user = userService.registerUser(registerUser);
            String token = userService.generateToken(user);
            return new JwtTokenDto(token);
        }
        catch (EntityExistsException e) {
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
        try {
            User user = userService.authenticateUser(loginRequestDto.getLogin(), loginRequestDto.getPassword());
            String token = userService.generateToken(user);
            return new JwtTokenDto(token);
        }
        catch (AuthenticationException e) {
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
        Optional<User> foundUser = userService.findUserByEmail(principal.getName());
        if(foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
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
        Optional<User> foundUser = userService.findUserById(id);
        if(foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.userToUserDTO(foundUser.get());
    }


    @Operation(summary = "Delete current user info", description = "Delete current user info")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(Principal principal) {
        Optional<User> foundUser = userService.findUserByEmail(principal.getName());
        if(foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteUser(foundUser.get());
    }
}
