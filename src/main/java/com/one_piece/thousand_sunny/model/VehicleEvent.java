package com.one_piece.thousand_sunny.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleEvent {

    private Long vehicleId;
    private Long userId;
    private String type;
    private String message;
}