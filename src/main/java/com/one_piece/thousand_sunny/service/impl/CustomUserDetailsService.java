package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new CustomUserDetails(user);
    }
}
