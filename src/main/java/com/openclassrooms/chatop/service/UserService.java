package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findUserByEmail(final String email);
    public Optional<User> findUserById(final long id);
}
