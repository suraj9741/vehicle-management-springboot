package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.converter.UserConverter;
import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.repository.RoleRepository;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public User assignRole(Long userId, String roleName) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);

        user = userRepository.save(user);

        return userConverter.convertEntityToModel(user);
    }
}