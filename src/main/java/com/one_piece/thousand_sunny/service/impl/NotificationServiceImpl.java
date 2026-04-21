package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.model.VehicleEvent;
import com.one_piece.thousand_sunny.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @KafkaListener(topics = "vehicle-events", groupId = "notification-group")
    public void consume(VehicleEvent event) {

        log.info("Received event from Kafka: {}", event);

        processEvent(event);
    }

    @Override
    public void processEvent(VehicleEvent event) {

        switch (event.getType()) {

            case "BATTERY_LOW":
                sendNotification("Battery low for vehicle " + event.getVehicleId());
                break;

            case "DOOR_OPEN":
                sendNotification("Door is open for vehicle " + event.getVehicleId());
                break;

            case "LIGHT_ON_WHEN_OFF":
                sendNotification("Lights ON while engine OFF for vehicle " + event.getVehicleId());
                break;

            case "SERVICE_DUE":
                sendNotification("Service due for vehicle " + event.getVehicleId());
                break;

            default:
                log.warn("Ignored event type: {}", event.getType());
        }
    }

    private void sendNotification(String message) {

        log.info("NOTIFICATION: {}", message);

        // Later:
        // emailService.send(message);
        // smsService.send(message);
    }
}