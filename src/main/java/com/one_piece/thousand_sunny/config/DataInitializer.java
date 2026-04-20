package com.one_piece.thousand_sunny.config;

import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.repository.RoleRepository;
import com.one_piece.thousand_sunny.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Create roles
        createRoleIfNotExists("USER");
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("CREATOR");

        // Create default admin
        createAdminIfNotExists();
    }

    private void createRoleIfNotExists(String roleName) {

        roleRepository.findByRoleName(roleName)
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                });
    }

    private void createAdminIfNotExists() {

        String adminEmail = "admin@example.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {

            RoleEntity adminRole = roleRepository.findByRoleName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            UserEntity admin = new UserEntity();
            admin.setName("Super Admin");
            admin.setEmail(adminEmail);

            // Always encode password
            admin.setPassword(passwordEncoder.encode("admin123"));

            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);

            System.out.println("Default ADMIN created: " + adminEmail);
        }
    }
}