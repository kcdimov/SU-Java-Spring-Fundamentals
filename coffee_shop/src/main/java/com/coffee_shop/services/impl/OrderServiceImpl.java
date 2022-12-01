package com.coffee_shop.services.impl;

import com.coffee_shop.models.entities.OrderEntity;
import com.coffee_shop.models.services.OrderServiceModel;
import com.coffee_shop.models.view.OrderViewModel;
import com.coffee_shop.repositories.OrderRepository;
import com.coffee_shop.security.CurrentUser;
import com.coffee_shop.services.CategoryService;
import com.coffee_shop.services.OrderService;
import com.coffee_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CurrentUser currentUser;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper,
                            UserService userService, CategoryService categoryService, CurrentUser currentUser) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.currentUser = currentUser;
    }

    @Override
    public void addOrder(OrderServiceModel orderServiceModel) {
        OrderEntity order = this.modelMapper.map(orderServiceModel, OrderEntity.class);

        order.setEmployee(userService.findById(currentUser.getId()));
        order.setCategory(categoryService.findCategoryByCategoryNameEnum(orderServiceModel.getCategory()));

        this.orderRepository.save(order);

    }

    @Override
    public List<OrderViewModel> findAllOrdersByPriceDecs() {

        return this.orderRepository.findAllByOrderByPriceDesc()
                .stream()
                .map(orderEntity -> this.modelMapper.map(orderEntity, OrderViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void orderReady(Long id) {
        this.orderRepository.deleteById(id);
    }
}
