package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Base URL for all endpoints in this controller
public class UserController {

    private final UserRepository userRepository;

    // Spring automatically injects the UserRepository via constructor injection
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. CREATE: Add a new user
    // HTTP POST http://localhost:8080/api/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println(user);
        return user;
    }

    // 2. READ ALL: Get a list of all users
    // HTTP GET http://localhost:8080/api/users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 3. READ ONE: Get a single user by ID
    // HTTP GET http://localhost:8080/api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. UPDATE: Modify an existing user's data
    // HTTP PUT http://localhost:8080/api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userDetails.getName());
                    existingUser.setEmail(userDetails.getEmail());
                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE: Remove a user from the database
    // HTTP DELETE http://localhost:8080/api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}