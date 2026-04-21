package com.one_piece.thousand_sunny.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleData {

    private Long vehicleId;
    private Long userId;

    private int batteryLevel;
    private boolean doorOpen;
    private boolean engineOn;
    private boolean lightsOn;
    private boolean serviceDue;
}
