package com.epam.esm.service;

import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    List<OrderDto> findByUserId(Long userId);
    OrderDto findById(Long orderId);
    OrderDto createOrder(OrderDto orderDto);
}
