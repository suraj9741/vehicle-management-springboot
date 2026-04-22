package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.converter.VehicleConverter;
import com.one_piece.thousand_sunny.entity.UserEntity;
import com.one_piece.thousand_sunny.entity.VehicleEntity;
import com.one_piece.thousand_sunny.model.Vehicle;
import com.one_piece.thousand_sunny.repository.UserRepository;
import com.one_piece.thousand_sunny.repository.VehicleRepository;
import com.one_piece.thousand_sunny.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;
    private final VehicleConverter vehicleConverter;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              VehicleConverter vehicleConverter,
                              UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleConverter = vehicleConverter;
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(value = {"vehicles", "vehiclesByUser"}, allEntries = true)
    public Vehicle register(Vehicle vehicle) {

        log.info("Registering vehicle with number: {}", vehicle.getVehicleNumber());

        VehicleEntity vehicleEntity = vehicleConverter.convertModelToEntity(vehicle);

        if (vehicle.getUserId() == null) {
            log.warn("UserId is null while registering vehicle");
            throw new RuntimeException("UserId is required to register vehicle");
        }

        UserEntity user = userRepository.findById(vehicle.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", vehicle.getUserId());
                    return new RuntimeException("User not found with id: " + vehicle.getUserId());
                });

        vehicleEntity.setUser(user);

        vehicleEntity = vehicleRepository.save(vehicleEntity);

        log.info("Vehicle registered successfully with id: {}", vehicleEntity.getId());
        log.info("CACHE EVICT → Clearing vehicle caches after register");

        return vehicleConverter.convertEntityToModel(vehicleEntity);
    }

    @Override
    @Cacheable(value = "vehiclesByUser", key = "#userId")
    public List<Vehicle> getVehiclesByUserId(Long userId) {

        log.debug("CACHE MISS → Fetching vehicles from DB for userId: {}", userId);

        List<VehicleEntity> vehicleEntities = vehicleRepository.findByUserId(userId);

        log.info("DB RESULT → Total vehicles found for userId {}: {}", userId, vehicleEntities.size());

        return vehicleEntities.stream()
                .map(vehicleConverter::convertEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "vehicles", key = "'all'")
    public List<Vehicle> getAll() {

        log.debug("CACHE MISS → Fetching all vehicles from DB");

        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();

        log.info("DB RESULT → Total vehicles fetched: {}", vehicleEntities.size());

        return vehicleEntities.stream()
                .map(vehicleConverter::convertEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"vehicles", "vehiclesByUser"}, allEntries = true)
    public Vehicle delete(Long id) {

        log.info("Deleting vehicle with id: {}", id);

        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Vehicle not found with id: {}", id);
                    return new RuntimeException("Vehicle not found with id: " + id);
                });

        vehicleRepository.delete(vehicleEntity);

        log.info("Vehicle deleted successfully with id: {}", id);
        log.info("CACHE EVICT → Clearing vehicle caches after delete");

        return vehicleConverter.convertEntityToModel(vehicleEntity);
    }
}