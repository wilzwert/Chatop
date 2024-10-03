package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.JwtTokenDto;
import com.openclassrooms.chatop.dto.RegisterUserDto;
import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.mapper.UserMapper;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserController(@Autowired final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public JwtTokenDto register(@Valid @RequestBody final RegisterUserDto registerUserDto) {
        User registerUser = new User();
        registerUser.setEmail(registerUserDto.getEmail());
        registerUser.setPassword(registerUserDto.getPassword());
        registerUser.setName(registerUserDto.getName());
        registerUser.setCreatedAt(LocalDateTime.now());
        registerUser.setUpdatedAt(LocalDateTime.now());

        User user = userService.registerUser(registerUser);
        return new JwtTokenDto("jwt");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String login() {
        return "TODO : login";
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String getUserInfo(Principal principal) {
        return "TODO";
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable long id) {
        Optional<User> foundUser = userService.findUserById(id);
        if(foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.userToUserDTO(foundUser.get());
    }
}
