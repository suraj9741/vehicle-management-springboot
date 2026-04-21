package com.one_piece.thousand_sunny.service;

import com.one_piece.thousand_sunny.model.VehicleData;
import com.one_piece.thousand_sunny.model.VehicleEvent;

import java.util.List;

public interface VehicleEventEvaluatorService {

    List<VehicleEvent> evaluate(VehicleData data);
}
