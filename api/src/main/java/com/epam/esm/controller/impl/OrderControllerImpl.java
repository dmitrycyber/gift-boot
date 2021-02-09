package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        List<OrderDto> orderDtoList = orderService.findByUserId(userId, pageNumber, pageSize);
        addSelfLinksToList(orderDtoList);
        return orderDtoList;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> allOrders(Integer pageNumber, Integer pageSize) {
        List<OrderDto> orderDtoList = orderService.findAll(pageNumber, pageSize);
        addSelfLinksToList(orderDtoList);
        return orderDtoList;
    }

    @Override
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto orderById(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        addSelfLinks(orderDto);
        return orderDto;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder(@RequestBody @Valid @Validated(CreatingDto.class) OrderDto orderDto) {
        OrderDto savedOrder = orderService.createOrder(orderDto);
        addSelfLinks(savedOrder);
        return savedOrder;
    }

    private void addSelfLinksToList(List<OrderDto> orderDtos) {
        for (OrderDto orderDto : orderDtos) {
            addSelfLinks(orderDto);
        }
    }

    private void addSelfLinks(OrderDto orderDto) {
        orderDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(OrderControllerImpl.class)
                        .orderById(orderDto.getId()))
                .withSelfRel());
    }
}
