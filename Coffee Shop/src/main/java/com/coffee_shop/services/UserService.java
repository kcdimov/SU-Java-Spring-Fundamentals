package com.coffee_shop.services;


import com.coffee_shop.models.entities.UserEntity;
import com.coffee_shop.models.services.UserServiceModel;
import com.coffee_shop.models.view.UserViewModel;

import java.util.List;

public interface UserService {
    boolean registerUser(UserServiceModel userServiceModel);

    void loginUser(Long id, String username);

    void logout();

    UserServiceModel findByUsernameAndPassword(String username, String password);

    UserEntity findById(Long id);

    List<UserViewModel> findAllUsersByCountDesc();

    boolean isUsernameIsFree(String username);

}
