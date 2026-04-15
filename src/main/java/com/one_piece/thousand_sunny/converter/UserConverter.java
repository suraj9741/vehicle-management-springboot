package com.one_piece.thousand_sunny.converter;

import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    @Autowired
    private VehicleConverter vehicleConverter;

    @Autowired
    private RoleConverter roleConverter;

    // Model → Entity
    public UserEntity convertModelToEntity(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());

        // Convert roles
        if (user.getRoles() != null) {
            userEntity.setRoles(
                    user.getRoles().stream().map(roleConverter::convertModelToEntity).collect(Collectors.toSet())
            );
        }

        // Vehicles usually handled separately (avoid recursion)
        return userEntity;
    }

    // Entity → Model
    public User convertEntityToModel(UserEntity userEntity) {
        User user = new User();

        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());

        // Convert roles
        if (userEntity.getRoles() != null) {
            user.setRoles(
                    userEntity.getRoles().stream().map(roleConverter::convertEntityToModel).collect(Collectors.toSet())
            );
        }

        // Convert vehicles
        if (userEntity.getVehicles() != null) {
            user.setVehicles(
                    userEntity.getVehicles().stream().map(vehicleConverter::convertEntityToModel).collect(Collectors.toList())
            );
        }

        return user;
    }
}