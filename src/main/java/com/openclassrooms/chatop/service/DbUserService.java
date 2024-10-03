package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DbUserService implements UserService {

    private final UserRepository userRepository;

    public DbUserService(@Autowired final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(final long id) {
        return userRepository.findById(id);
    }
}
