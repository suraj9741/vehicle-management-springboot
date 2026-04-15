package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.common.ApiResponse;
import com.one_piece.thousand_sunny.model.Vehicle;
import com.one_piece.thousand_sunny.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@Validated
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Create vehicle
    @PostMapping
    public ResponseEntity<ApiResponse<Vehicle>> createVehicle(@RequestBody @Valid Vehicle vehicle) {

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("success", "Vehicle created successfully", vehicleService.register(vehicle)));
    }

    // Get vehicles by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Vehicle>>> getVehiclesByUserId(@PathVariable @Min(value = 1, message = "UserId must be greater than 0") Long userId) {

        return ResponseEntity.ok(new ApiResponse<>("success", "Vehicles fetched for user", vehicleService.getVehiclesByUserId(userId)));
    }

    // Get all vehicles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Vehicle>>> getAllVehicles() {

        return ResponseEntity.ok(new ApiResponse<>("success", "Vehicles fetched successfully", vehicleService.getAll()));
    }

    // Delete vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteVehicle(@PathVariable @Min(value = 1, message = "VehicleId must be greater than 0") Long id) {

        vehicleService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Vehicle deleted successfully", null));
    }
}