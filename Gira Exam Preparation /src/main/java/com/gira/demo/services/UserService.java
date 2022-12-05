package com.gira.demo.services;

import com.gira.demo.models.services.UserServiceModel;

public interface UserService {
    boolean existByEmail(String email);

    boolean existBuUsername(String username);

    UserServiceModel registerUser(UserServiceModel map);

    UserServiceModel findUserByEmail(String email);

    UserServiceModel findUserById(String id);

}
