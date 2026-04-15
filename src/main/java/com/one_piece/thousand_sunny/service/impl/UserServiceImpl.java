package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.converter.UserConverter;
import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.repository.RoleRepository;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public User register(User user) {

        // Convert model → entity
        UserEntity userEntity = userConverter.convertModelToEntity(user);

        // Assign default role (USER)
        RoleEntity role = roleRepository.findByRoleName("USER").orElseThrow(() -> new RuntimeException("Default role USER not found"));

        userEntity.setRoles(Collections.singleton(role));

        // Save user
        userEntity = userRepository.save(userEntity);

        // Convert back → model
        return userConverter.convertEntityToModel(userEntity);
    }

    @Override
    public User getById(Long id) {

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return userConverter.convertEntityToModel(userEntity);
    }

    @Override
    public List<User> getAll() {

        List<UserEntity> users = userRepository.findAll();

        return users.stream().map(userConverter::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {

        // Fetch existing user
        UserEntity existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update fields (only allowed fields)
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        // Optional: update password if provided
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(user.getPassword());
        }

        // Save updated entity
        existingUser = userRepository.save(existingUser);

        // Convert back to model
        return userConverter.convertEntityToModel(existingUser);
    }
}