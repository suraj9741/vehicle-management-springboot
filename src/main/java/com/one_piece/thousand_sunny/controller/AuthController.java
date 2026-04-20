package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.common.ApiResponse;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.model.LoginRequest;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.security.JwtUtil;
import com.one_piece.thousand_sunny.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserEntity user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(
                    new ApiResponse<>("success", "Login successful", token)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("error", "Invalid email or password", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody @Valid User user) {

        User savedUser = userService.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("success", "User created successfully", savedUser));
    }
}