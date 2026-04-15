package com.one_piece.thousand_sunny.config;

import com.one_piece.thousand_sunny.entity.RoleEntity;
import com.one_piece.thousand_sunny.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRoleIfNotExists("USER");
        createRoleIfNotExists("ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {

        roleRepository.findByRoleName(roleName).orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                });
    }
}