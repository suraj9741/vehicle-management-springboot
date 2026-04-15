package com.one_piece.thousand_sunny.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;
}