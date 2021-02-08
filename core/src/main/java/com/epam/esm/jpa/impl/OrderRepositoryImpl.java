package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.OrderRepository;
import com.epam.esm.jpa.exception.OrderNotFoundException;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderEntity> findAll() {
        return entityManager.createQuery("select orderEntity from OrderEntity orderEntity", OrderEntity.class).getResultList();
    }

    @Override
    public List<OrderEntity> findByUserId(Long userId) {
        List<OrderEntity> resultList = entityManager.createQuery("select oe from OrderEntity oe WHERE oe.userEntity.id = :userId", OrderEntity.class)
                .setParameter("userId", userId)
                .getResultList();

        log.info("USER BY ID " + resultList);

        return resultList;
    }

    @Override
    public OrderEntity findById(Long orderId) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
        if (orderEntity == null){
            throw new OrderNotFoundException(orderId.toString());
        }
        return orderEntity;
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }
}
