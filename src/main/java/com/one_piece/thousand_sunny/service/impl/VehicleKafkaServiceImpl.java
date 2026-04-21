package com.one_piece.thousand_sunny.service.impl;

import com.one_piece.thousand_sunny.model.VehicleData;
import com.one_piece.thousand_sunny.model.VehicleEvent;
import com.one_piece.thousand_sunny.service.VehicleEventEvaluatorService;
import com.one_piece.thousand_sunny.service.VehicleKafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VehicleKafkaServiceImpl implements VehicleKafkaService {

    private static final Logger log = LoggerFactory.getLogger(VehicleKafkaServiceImpl.class);

    private static final String VEHICLE_DATA_TOPIC = "vehicle-data";
    private static final String VEHICLE_EVENT_TOPIC = "vehicle-events";

    // Kafka templates
    private final KafkaTemplate<String, VehicleData> vehicleDataKafkaTemplate;
    private final KafkaTemplate<String, VehicleEvent> vehicleEventKafkaTemplate;

    // Evaluator
    private final VehicleEventEvaluatorService evaluator;

    // Cache
    private final List<VehicleData> consumedCache = new CopyOnWriteArrayList<>();

    public VehicleKafkaServiceImpl(
            KafkaTemplate<String, VehicleData> vehicleDataKafkaTemplate,
            KafkaTemplate<String, VehicleEvent> vehicleEventKafkaTemplate,
            VehicleEventEvaluatorService evaluator
    ) {
        this.vehicleDataKafkaTemplate = vehicleDataKafkaTemplate;
        this.vehicleEventKafkaTemplate = vehicleEventKafkaTemplate;
        this.evaluator = evaluator;
    }

    // PRODUCER (vehicle-data)
    @Override
    public void produceVehicleData(VehicleData data) {
        vehicleDataKafkaTemplate.send(VEHICLE_DATA_TOPIC, String.valueOf(data.getVehicleId()), data);
        log.info("Produced VehicleData for vehicleId: {}", data.getVehicleId());
        log.debug("Produced VehicleData payload: {}", data);
    }

    // CONSUMER (vehicle-data → evaluate → vehicle-events)
    @Override
    @KafkaListener(topics = VEHICLE_DATA_TOPIC, groupId = "vehicle-data-group")
    public void consumeVehicleData(VehicleData data) {

        log.info("Consumed VehicleData for vehicleId: {}", data.getVehicleId());
        log.debug("Consumed VehicleData payload: {}", data);

        // cache
        consumedCache.add(data);

        // STEP 1: Evaluate
        List<VehicleEvent> events = evaluator.evaluate(data);

        log.info("Generated {} events for vehicleId: {}", events.size(), data.getVehicleId());

        // STEP 2: Publish to vehicle-events topic
        for (VehicleEvent event : events) {
            vehicleEventKafkaTemplate.send(
                    VEHICLE_EVENT_TOPIC,
                    String.valueOf(event.getVehicleId()),
                    event
            );

            log.info("Published VehicleEvent type: {} for vehicleId: {}", event.getType(), event.getVehicleId());
            log.debug("Published VehicleEvent payload: {}", event);
        }
    }

    // Getter
    @Override
    public List<VehicleData> getConsumedData() {
        return consumedCache;
    }
}