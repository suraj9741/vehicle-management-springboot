package com.one_piece.thousand_sunny.service;

import com.one_piece.thousand_sunny.model.Vehicle;

import java.util.List;

public interface VehicleService {

    // Create vehicle
    Vehicle register(Vehicle vehicle);
    // Get vehicles by userId
    List<Vehicle> getVehiclesByUserId(Long userId);
    // Get all vehicles
    List<Vehicle> getAll();

    Vehicle delete(Long id);
}