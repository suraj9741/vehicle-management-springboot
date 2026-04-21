package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        log.debug("Attempting to load user by email: {}", email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new RuntimeException("User not found");
                });

        log.info("User found with email: {}", email);

        return new CustomUserDetails(user);
    }
}