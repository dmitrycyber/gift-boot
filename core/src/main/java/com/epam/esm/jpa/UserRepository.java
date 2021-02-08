package com.epam.esm.jpa;

import com.epam.esm.model.entity.UserEntity;

public interface UserRepository {
    UserEntity register(UserEntity userEntity);
    UserEntity findById(Long userId);
}
