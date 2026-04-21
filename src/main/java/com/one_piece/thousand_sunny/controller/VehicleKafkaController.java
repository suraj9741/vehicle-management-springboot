package com.one_piece.thousand_sunny.controller;

import com.one_piece.thousand_sunny.common.ApiResponse;
import com.one_piece.thousand_sunny.model.VehicleData;
import com.one_piece.thousand_sunny.service.VehicleKafkaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kafka")
public class VehicleKafkaController {

    private final VehicleKafkaService vehicleKafkaService;

    public VehicleKafkaController(VehicleKafkaService vehicleKafkaService) {
        this.vehicleKafkaService = vehicleKafkaService;
    }

    // Publish vehicle data to Kafka
    @PostMapping("/publish")
    public ResponseEntity<ApiResponse<String>> publishVehicleData(
            @RequestBody @Valid VehicleData vehicleData) {

        vehicleKafkaService.produceVehicleData(vehicleData);

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Vehicle data published to Kafka", null)
        );
    }

    // View consumed data
    @GetMapping("/data")
    public ResponseEntity<ApiResponse<List<VehicleData>>> getConsumedData() {

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Consumed Kafka data", vehicleKafkaService.getConsumedData())
        );
    }
    
    // Dummy endpoint to confirm consumer is working
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> kafkaStatus() {

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Kafka consumer is running (check logs)", null)
        );
    }
}