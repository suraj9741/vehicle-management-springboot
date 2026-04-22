package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.converter.UserConverter;
import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.repository.RoleRepository;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserConverter userConverter,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User register(User user) {

        log.info("Registering new user with email: {}", user.getEmail());

        // Convert model → entity
        UserEntity userEntity = userConverter.convertModelToEntity(user);

        // Encode password (NEVER log password)
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role
        RoleEntity role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> {
                    log.error("Default role USER not found");
                    return new RuntimeException("Default role USER not found");
                });

        userEntity.setRoles(Collections.singleton(role));

        // Save user
        userEntity = userRepository.save(userEntity);

        log.info("User registered successfully with id: {}", userEntity.getId());
        log.info("CACHE EVICT → Clearing user cache after register");

        return userConverter.convertEntityToModel(userEntity);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public User getById(Long id) {

        log.debug("CACHE MISS → Fetching user from DB with id: {}", id);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", id);
                    return new RuntimeException("User not found with id: " + id);
                });

        return userConverter.convertEntityToModel(userEntity);
    }

    @Override
    @Cacheable(value = "users", key = "'all'")
    public List<User> getAll() {

        log.debug("CACHE MISS → Fetching all users from DB");

        List<UserEntity> users = userRepository.findAll();

        log.info("DB RESULT → Total users fetched: {}", users.size());

        return users.stream()
                .map(userConverter::convertEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void delete(Long id) {

        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("Attempt to delete non-existing user with id: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);

        log.info("User deleted successfully with id: {}", id);
        log.info("CACHE EVICT → Clearing user cache after delete");
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User update(Long id, User user) {

        log.info("Updating user with id: {}", id);

        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update with id: {}", id);
                    return new RuntimeException("User not found with id: " + id);
                });

        // Update allowed fields
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            log.debug("Password updated for user id: {}", id);
        }

        existingUser = userRepository.save(existingUser);

        log.info("User updated successfully with id: {}", id);
        log.info("CACHE EVICT → Clearing user cache after update");

        return userConverter.convertEntityToModel(existingUser);
    }
}