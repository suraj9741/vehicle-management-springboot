package com.one_piece.thousand_sunny.repository;

import com.one_piece.thousand_sunny.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Find User by Email
    Optional<UserEntity> findByEmail(String email);
}