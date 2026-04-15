package com.one_piece.thousand_sunny.converter;

import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    // Model → Entity
    public RoleEntity convertModelToEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId(role.getId());
        roleEntity.setRoleName(role.getRoleName());

        return roleEntity;
    }

    // Entity → Model
    public Role convertEntityToModel(RoleEntity roleEntity) {
        Role role = new Role();

        role.setId(roleEntity.getId());
        role.setRoleName(roleEntity.getRoleName());

        return role;
    }
}