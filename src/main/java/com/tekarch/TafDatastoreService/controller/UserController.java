package com.tekarch.TafDatastoreService.controller;
import com.tekarch.TafDatastoreService.model.User;
import com.tekarch.TafDatastoreService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        // Set the ID of the updated user
        user.setId(userId);

        // Retain the createdAt (do not modify it)
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setCreatedAt(existingUser.getCreatedAt());  // Retain the original createdAt

        // Set the updatedAt to the current timestamp
        user.setUpdatedAt(LocalDateTime.now()); // Set updatedAt on update

        return userRepository.save(user);
    }
}
