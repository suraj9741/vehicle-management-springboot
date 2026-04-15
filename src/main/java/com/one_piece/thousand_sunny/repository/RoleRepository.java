package com.one_piece.thousand_sunny.repository;

import com.one_piece.thousand_sunny.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    // Find role by name (ADMIN / USER)
    Optional<RoleEntity> findByRoleName(String roleName);
}