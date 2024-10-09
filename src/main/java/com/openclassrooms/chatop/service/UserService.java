package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

/**
 * @author Wilhelm Zwertvaegher
 */
public interface UserService {
    User registerUser(User user);
    User authenticateUser(String email, String password) throws AuthenticationException;

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(final long id);
    String encodePassword(String password);
    String generateToken(User user);
    void deleteUser(User user);
}
