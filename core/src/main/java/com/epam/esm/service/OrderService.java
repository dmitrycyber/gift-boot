package com.epam.esm.service;

import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll(Integer pageNumber, Integer pageSize);
    List<OrderDto> findByUserId(Long userId, Integer pageNumber, Integer pageSize);
    OrderDto findById(Long orderId);
    OrderDto createOrder(OrderDto orderDto);
}
