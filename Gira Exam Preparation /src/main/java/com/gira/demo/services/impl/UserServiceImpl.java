package com.gira.demo.services.impl;

import com.gira.demo.models.entities.User;
import com.gira.demo.models.services.UserServiceModel;
import com.gira.demo.repositories.UserRepository;
import com.gira.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);

    }

    @Override
    public boolean existBuUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        return this.modelMapper.map(this.userRepository.save(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        return modelMapper.map(user, UserServiceModel.class);
    }

}
