package com.one_piece.thousand_sunny.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;

    // One user → many vehicles
    private List<Vehicle> vehicles;

    // Many-to-Many → roles
    private Set<Role> roles;
}