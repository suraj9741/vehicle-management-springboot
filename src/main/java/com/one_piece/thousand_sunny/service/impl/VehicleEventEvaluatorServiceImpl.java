package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.model.VehicleData;
import com.one_piece.thousand_sunny.model.VehicleEvent;
import com.one_piece.thousand_sunny.model.VehicleEventType;
import com.one_piece.thousand_sunny.service.VehicleEventEvaluatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleEventEvaluatorServiceImpl implements VehicleEventEvaluatorService {

    private static final Logger log = LoggerFactory.getLogger(VehicleEventEvaluatorServiceImpl.class);

    @Override
    public List<VehicleEvent> evaluate(VehicleData data) {

        if (data == null) {
            log.warn("Received null VehicleData for evaluation");
            return List.of();
        }

        log.debug("Evaluating VehicleData for vehicleId: {}", data.getVehicleId());

        List<VehicleEvent> events = new ArrayList<>();

        if (data.getBatteryLevel() < 20) {
            log.info("Battery low detected for vehicleId: {}", data.getVehicleId());
            events.add(create(data, VehicleEventType.BATTERY_LOW, "Battery is low"));
        }

        if (data.isDoorOpen()) {
            log.info("Door open detected for vehicleId: {}", data.getVehicleId());
            events.add(create(data, VehicleEventType.DOOR_OPEN, "Door is open"));
        }

        if (!data.isEngineOn() && data.isLightsOn()) {
            log.info("Lights ON while engine OFF for vehicleId: {}", data.getVehicleId());
            events.add(create(data, VehicleEventType.LIGHT_ON_WHEN_OFF, "Lights ON while engine OFF"));
        }

        if (data.isServiceDue()) {
            log.info("Service due detected for vehicleId: {}", data.getVehicleId());
            events.add(create(data, VehicleEventType.SERVICE_DUE, "Service is due"));
        }

        log.debug("Total events generated for vehicleId {}: {}", data.getVehicleId(), events.size());

        return events;
    }

    private VehicleEvent create(VehicleData data, VehicleEventType type, String msg) {

        log.debug("Creating event {} for vehicleId: {}", type, data.getVehicleId());

        return VehicleEvent.builder()
                .vehicleId(data.getVehicleId())
                .userId(data.getUserId())
                .type(type.name())
                .message(msg)
                .build();
    }
}