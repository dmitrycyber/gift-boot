package com.epam.esm.jpa;

import com.epam.esm.model.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity register(UserEntity userEntity);
    UserEntity findById(Long userId);
    List<UserEntity> findAll(Integer pageNumber, Integer pageSize);
}
