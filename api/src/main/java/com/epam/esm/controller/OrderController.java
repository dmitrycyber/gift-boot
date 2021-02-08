package com.epam.esm.controller;

import com.epam.esm.model.dto.order.OrderDto;

import java.util.List;

public interface OrderController {
    List<OrderDto> ordersByUserId(Long userId);

    OrderDto orderById(Long orderId);

    OrderDto createOrder(OrderDto orderDto);
}
