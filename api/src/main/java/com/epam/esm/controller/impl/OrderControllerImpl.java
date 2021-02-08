package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    @Override
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> userOrders(@PathVariable Long userId, Integer pageNumber, Integer pageSize) {
        return orderService.findByUserId(userId, pageNumber, pageSize);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> allOrders(Integer pageNumber, Integer pageSize) {
        return orderService.findAll(pageNumber, pageSize);
    }

    @Override
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto orderById(@PathVariable Long orderId) {
        return orderService.findById(orderId);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder(@RequestBody @Valid @Validated(CreatingDto.class) OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
