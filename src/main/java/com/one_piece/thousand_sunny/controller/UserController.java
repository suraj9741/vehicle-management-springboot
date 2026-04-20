package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.common.ApiResponse;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // Create user (register)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CREATOR')")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody @Valid User user) {
        User savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("success", "User created successfully", savedUser));
    }

    // Get user by ID (validated)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == principal.id)")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id) {

        System.out.println("Principal class: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass());
        return ResponseEntity.ok(new ApiResponse<>("success", "User fetched successfully", userService.getById(id)));
    }

    // Update user (validated)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == principal.id)")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id, @RequestBody @Valid User user) {

        return ResponseEntity.ok(new ApiResponse<>("success", "User updated successfully", userService.update(id, user)));
    }

    // Delete user (validated)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CREATOR')")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id) {

        userService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "User deleted successfully", null));
    }
}