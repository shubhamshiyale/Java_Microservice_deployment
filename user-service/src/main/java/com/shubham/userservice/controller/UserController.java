package com.shubham.userservice.controller;

import com.shubham.userservice.model.User;
import com.shubham.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")  // All routes will be under /users
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Optional root URL to prevent Whitelabel error
    @GetMapping("/")
    public String home() {
        return "User Service is up!";
    }

    // Add a PUT method to update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if user not found
        }

        // Update user fields
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        // Save and return the updated user
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);  // Return the updated user with 200 OK status
    }
}
