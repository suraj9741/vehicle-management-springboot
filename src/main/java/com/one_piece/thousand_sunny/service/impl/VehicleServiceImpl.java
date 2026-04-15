package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.converter.VehicleConverter;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.entity.VehicleEntity;
import com.one_piece.thousand_sunny.model.Vehicle;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.repository.VehicleRepository;
import com.one_piece.thousand_sunny.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleConverter vehicleConverter;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Vehicle register(Vehicle vehicle) {

        VehicleEntity vehicleEntity = vehicleConverter.convertModelToEntity(vehicle);

        if (vehicle.getUserId() == null) {
            throw new RuntimeException("UserId is required to register vehicle");
        }
        UserEntity user = userRepository.findById(vehicle.getUserId()).orElseThrow(() -> new RuntimeException("User not found with id: " + vehicle.getUserId()));
        vehicleEntity.setUser(user);

        vehicleEntity = vehicleRepository.save(vehicleEntity);

        return vehicleConverter.convertEntityToModel(vehicleEntity);
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Long userId) {

        List<VehicleEntity> vehicleEntities = vehicleRepository.findByUserId(userId);

        return vehicleEntities.stream().map(vehicleConverter::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> getAll() {
        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();

        return vehicleEntities.stream().map(vehicleConverter::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    public Vehicle delete(Long id) {

        VehicleEntity vehicleEntity = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        vehicleRepository.delete(vehicleEntity);

        return vehicleConverter.convertEntityToModel(vehicleEntity);
    }
}