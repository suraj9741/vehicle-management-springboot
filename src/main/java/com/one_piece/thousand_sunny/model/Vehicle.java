package com.one_piece.thousand_sunny.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {
    private Long id;
    private String model;
    private String vehicleNumber;
    private String status;
    private Long userId;
}
