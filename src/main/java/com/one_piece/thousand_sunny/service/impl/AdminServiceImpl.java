package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.converter.UserConverter;
import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.repository.RoleRepository;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;

    public AdminServiceImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            UserConverter userConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
    }

    @Override
    public User assignRole(Long userId, String roleName) {

        log.info("Assigning role '{}' to user with id {}", roleName, userId);

        // Fetch user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", userId);
                    return new RuntimeException("User not found");
                });

        // Fetch role
        RoleEntity role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> {
                    log.error("Role '{}' not found", roleName);
                    return new RuntimeException("Role not found");
                });

        // Assign role
        user.getRoles().add(role);

        // Save
        user = userRepository.save(user);

        log.info("Successfully assigned role '{}' to user with id {}", roleName, userId);

        return userConverter.convertEntityToModel(user);
    }
}