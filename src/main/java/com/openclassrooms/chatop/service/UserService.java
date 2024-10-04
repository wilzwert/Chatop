package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;

import java.util.Optional;

public interface UserService {
    public User registerUser(User user);
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserById(final long id);
    public String encodePassword(String password);
}
