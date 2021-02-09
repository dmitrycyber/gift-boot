package com.epam.esm.service.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.OrderRepository;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Override
    public List<OrderDto> findAll(Integer pageNumber, Integer pageSize) {
        List<OrderEntity> orderEntityList = orderRepository.findAll(pageNumber, pageSize);
        return orderEntityList.stream()
                .map(EntityConverter::convertOrderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByUserId(Long userId, Integer pageNumber, Integer pageSize) {
        List<OrderEntity> orderEntityList = orderRepository.findByUserId(userId, pageNumber, pageSize);

        return orderEntityList.stream()
                .map(EntityConverter::convertOrderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);

        return EntityConverter.convertOrderEntityToDto(orderEntity);
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Long giftId = orderDto.getGiftId();
        GiftCertificateEntity giftCertificateEntity = giftCertificateRepository.findById(giftId);

        OrderEntity orderEntity = EntityConverter.convertOrderDtoToEntity(orderDto);
        orderEntity.setCost(giftCertificateEntity.getPrice());

        OrderEntity savedOrder = orderRepository.createOrder(orderEntity);

        return EntityConverter.convertOrderEntityToDto(savedOrder);
    }
}
