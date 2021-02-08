package com.epam.esm.service;

import com.epam.esm.model.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto register(UserDto userDto);

    UserDto userProfile(Long userId);

    List<UserDto> findAll(Integer pageNumber, Integer pageSize);
}
