package com.one_piece.thousand_sunny.repository;

import com.one_piece.thousand_sunny.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    // Get vehicles by user
    List<VehicleEntity> findByUserId(Long userId);

    // Optional: find by vehicle number
    VehicleEntity findByVehicleNumber(String vehicleNumber);
}