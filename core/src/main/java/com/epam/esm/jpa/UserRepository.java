package com.epam.esm.jpa;

import com.epam.esm.model.entity.UserEntity;

import java.util.List;

public interface UserRepository {

    /**
     * Create user
     * @param userEntity entity to save
     * @return saved user
     */
    UserEntity register(UserEntity userEntity);

    /**
     * Find user by user id
     * @param userId order id
     * @return UserEntity
     * @throws com.epam.esm.jpa.exception.UserNotFoundException if such user not exist
     */
    UserEntity findById(Long userId);

    /**
     * Find all users
     * @return List UserEntity
     * @param pageNumber pagination
     * @param pageSize pagination
     * if fount no users - return empty list
     */
    List<UserEntity> findAll(Integer pageNumber, Integer pageSize);
}
