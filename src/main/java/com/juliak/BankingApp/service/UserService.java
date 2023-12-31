package com.juliak.BankingApp.service;

import com.juliak.BankingApp.model.User;
import com.juliak.BankingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Fetch user details by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new Error("User not found with ID: " + id));
    }

    // Fetch user details by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new Error("User not found with username: " + username));
    }

}
