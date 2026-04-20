package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.common.ApiResponse;
import com.one_piece.thousand_sunny.model.User;
import com.one_piece.thousand_sunny.model.Vehicle;
import com.one_piece.thousand_sunny.service.AdminService;
import com.one_piece.thousand_sunny.service.UserService;
import com.one_piece.thousand_sunny.service.VehicleService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Validated
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        return ResponseEntity.ok(new ApiResponse<>("success", "Users fetched successfully", userService.getAll()));
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long id) {

        userService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "User deleted by admin", null));
    }

    // Assign role
    @PutMapping("/users/{userId}/roles/{roleName}")
    public ResponseEntity<ApiResponse<User>> assignRole(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long userId, @PathVariable String roleName) {

        return ResponseEntity.ok(new ApiResponse<>("success", "Role assigned successfully", adminService.assignRole(userId, roleName)));
    }

    // ================= VEHICLE ADMIN OPS =================

    // Get all vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<ApiResponse<List<Vehicle>>> getAllVehicles() {

        return ResponseEntity.ok(new ApiResponse<>("success", "Vehicles fetched successfully", vehicleService.getAll()));
    }

    // Delete vehicle
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteVehicle(@PathVariable @Min(value = 1, message = "VehicleId must be greater than 0") Long id) {

        vehicleService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Vehicle deleted by admin", null));
    }

    // Get vehicles by user
    @GetMapping("/vehicles/user/{userId}")
    public ResponseEntity<ApiResponse<List<Vehicle>>> getVehiclesByUser(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long userId) {

        return ResponseEntity.ok(new ApiResponse<>("success", "Vehicles fetched for user", vehicleService.getVehiclesByUserId(userId)));
    }
}