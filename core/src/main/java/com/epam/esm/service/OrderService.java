package com.epam.esm.service;

import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    /**
     * Find all orders
     * @return List Order Dto
     * @param pageNumber pagination
     * @param pageSize pagination
     * if fount no orders - return empty list
     */
    List<OrderDto> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Find orders by user id
     * @param userId order id
     * @return OrderDto
     * @throws com.epam.esm.jpa.exception.UserNotFoundException from repository layer if such user not exist
     */
    List<OrderDto> findByUserId(Long userId, Integer pageNumber, Integer pageSize);

    /**
     * Find orders by order id
     * @param orderId order id
     * @return OrderDto
     * @throws com.epam.esm.jpa.exception.OrderNotFoundException from repository layer if such order not exist
     */
    OrderDto findById(Long orderId);

    /**
     * Create order
     * @param orderDto with gift id and user id
     * @return created order
     */
    OrderDto createOrder(OrderDto orderDto);
}
