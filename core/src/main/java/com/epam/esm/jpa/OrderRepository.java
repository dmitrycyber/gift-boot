package com.epam.esm.jpa;

import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {
    List<OrderEntity> findAll();
    List<OrderEntity> findByUserId(Long userId);
    OrderEntity findById(Long orderId);
    OrderEntity createOrder(OrderEntity orderEntity);

}
