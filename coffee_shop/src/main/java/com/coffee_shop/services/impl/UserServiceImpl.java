package com.coffee_shop.services.impl;

import com.coffee_shop.models.entities.UserEntity;
import com.coffee_shop.models.services.UserServiceModel;
import com.coffee_shop.models.view.UserViewModel;
import com.coffee_shop.repositories.UserRepository;
import com.coffee_shop.security.CurrentUser;
import com.coffee_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }


    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);

        this.userRepository.save(userEntity);
        return false;
    }

    @Override
    public void loginUser(Long id, String username) {
     currentUser.setUsername(username).setId(id); //TODO current user
    }

    @Override
    public void logout() {
     currentUser.setUsername(null).setId(null); // TODO current user
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return this.userRepository.findByUsernameAndPassword(username, password)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserViewModel> findAllUsersByCountDesc() {


        return userRepository.findAllByOrdersByDesc()
                .stream()
                .map(user-> {
                    UserViewModel userViewModel=new UserViewModel();
                    userViewModel.setUsername(user.getUsername());
                    userViewModel.setOrdersCount(user.getOrders().size());
                    return userViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUsernameIsFree(String username) {
        return this.userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }
}
