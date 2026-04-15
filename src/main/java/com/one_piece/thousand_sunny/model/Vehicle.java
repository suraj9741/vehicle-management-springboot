package com.one_piece.thousand_sunny.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {

    private Long id;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Vehicle number is required")
    private String vehicleNumber;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "UserId is required")
    private Long userId;
}