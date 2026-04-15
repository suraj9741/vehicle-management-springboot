package com.one_piece.thousand_sunny.service;

import com.one_piece.thousand_sunny.model.User;


public interface AdminService {

    User assignRole(Long userId, String roleName);
}