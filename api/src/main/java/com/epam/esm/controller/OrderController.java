package com.epam.esm.controller;

import com.epam.esm.model.dto.order.OrderDto;

import java.util.List;

public interface OrderController {
    List<OrderDto> userOrders(Long userId, Integer pageNumber, Integer pageSize);

    List<OrderDto> allOrders(Integer pageNumber, Integer pageSize);

    OrderDto orderById(Long orderId);

    OrderDto createOrder(OrderDto orderDto);
}
