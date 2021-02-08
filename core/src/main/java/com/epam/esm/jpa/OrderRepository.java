package com.epam.esm.jpa;

import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {
    List<OrderEntity> findAll(Integer pageNumber, Integer pageSize);
    List<OrderEntity> findByUserId(Long userId, Integer pageNumber, Integer pageSize);
    OrderEntity findById(Long orderId);
    OrderEntity createOrder(OrderEntity orderEntity);

}
