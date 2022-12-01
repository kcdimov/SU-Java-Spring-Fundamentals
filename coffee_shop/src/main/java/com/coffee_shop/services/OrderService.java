package com.coffee_shop.services;

import com.coffee_shop.models.services.OrderServiceModel;
import com.coffee_shop.models.view.OrderViewModel;

import java.util.List;

public interface OrderService {
    void addOrder(OrderServiceModel orderServiceModel);

    List<OrderViewModel> findAllOrdersByPriceDecs();

    void orderReady(Long id);
}
