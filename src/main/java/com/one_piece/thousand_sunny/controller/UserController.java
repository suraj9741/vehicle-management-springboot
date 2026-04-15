package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // Create user (register)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Get user by ID (validated)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id) {

        return ResponseEntity.ok(userService.getById(id));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(userService.getAll());
    }

    // Update user (validated)
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id, @RequestBody @Valid User user) {

        return ResponseEntity.ok(userService.update(id, user));
    }

    // Delete user (validated)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id) {

        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}