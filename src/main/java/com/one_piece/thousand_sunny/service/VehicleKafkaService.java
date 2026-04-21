package com.one_piece.thousand_sunny.service;

import com.one_piece.thousand_sunny.model.VehicleData;

import java.util.List;

public interface VehicleKafkaService {

    void produceVehicleData(VehicleData data);

    void consumeVehicleData(VehicleData data);

    List<VehicleData> getConsumedData();
}