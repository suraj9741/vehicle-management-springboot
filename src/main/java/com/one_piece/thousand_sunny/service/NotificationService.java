package com.one_piece.thousand_sunny.service;

import com.one_piece.thousand_sunny.model.VehicleEvent;

public interface NotificationService {

    void processEvent(VehicleEvent event);
}