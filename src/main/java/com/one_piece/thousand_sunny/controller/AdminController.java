package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.model.Vehicle;
import com.one_piece.thousand_sunny.service.AdminService;
import com.one_piece.thousand_sunny.service.UserService;
import com.one_piece.thousand_sunny.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted by admin");
    }

    // Assign role (Admin-specific logic)
    @PutMapping("/users/{userId}/roles/{roleName}")
    public ResponseEntity<User> assignRole(@PathVariable Long userId, @PathVariable String roleName) {
        return ResponseEntity.ok(adminService.assignRole(userId, roleName));
    }

    // ================= VEHICLE ADMIN OPS =================

    // Get all vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAll());
    }

    // Delete vehicle
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.ok("Vehicle deleted by admin");
    }

    // Get vehicles by user
    @GetMapping("/vehicles/user/{userId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByUserId(userId));
    }
}