package com.coffee_shop.repositories;

import com.coffee_shop.models.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
        List<OrderEntity> findAllByOrderByPriceDesc();
//    List<OrderEntity> findAllByOrderByPriceDesc();
}
