package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

public interface UserService {
    public User registerUser(User user);
    public User authenticateUser(String email, String password) throws AuthenticationException;
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserById(final long id);
    public String encodePassword(String password);
    public String generateToken(User user);
}
