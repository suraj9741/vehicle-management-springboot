package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.model.Vehicle;
import com.one_piece.thousand_sunny.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Create vehicle
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody @Valid Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.register(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    // Get vehicles by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByUserId(@PathVariable Long userId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAll();
        return ResponseEntity.ok(vehicles);
    }

    // Delete vehicles by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
