package com.epam.esm.service;

import com.epam.esm.model.dto.user.UserDto;

public interface UserService {
    UserDto register(UserDto userDto);

    UserDto userProfile(Long userId);
}
