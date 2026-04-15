package com.one_piece.thousand_sunny.converter;

import com.one_piece.thousand_sunny.entity.VehicleEntity;
import com.one_piece.thousand_sunny.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleConverter {

    // Model → Entity
    public VehicleEntity convertModelToEntity(Vehicle vehicle){
        VehicleEntity vehicleEntity = new VehicleEntity();

        vehicleEntity.setModel(vehicle.getModel());
        vehicleEntity.setVehicleNumber(vehicle.getVehicleNumber());
        vehicleEntity.setStatus(vehicle.getStatus());
        return vehicleEntity;
    }

    // Entity → Model
    public Vehicle convertEntityToModel(VehicleEntity vehicleEntity){
        Vehicle vehicle = new Vehicle();

        vehicle.setId(vehicleEntity.getId());
        vehicle.setModel(vehicleEntity.getModel());
        vehicle.setVehicleNumber(vehicleEntity.getVehicleNumber());
        vehicle.setStatus(vehicleEntity.getStatus());
        if (vehicleEntity.getUser() != null) {
            vehicle.setUserId(vehicleEntity.getUser().getId());
        }
        return vehicle;
    }
}
