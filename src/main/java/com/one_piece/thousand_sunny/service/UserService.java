package com.one_piece.thousand_sunny.service;

import com.one_piece.thousand_sunny.model.User;
import java.util.List;

public interface UserService {

    // Register new user
    User register(User user);

    // Get user by ID
    User getById(Long id);

    // Get all users
    List<User> getAll();

    // Delete user
    void delete(Long id);

    // Update user details
    User update(Long id, User user);
}