package com.epam.esm.controller;

import com.epam.esm.model.dto.user.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController {
    UserDto register(UserDto userDto);

    UserDto userProfile(Long userId);

    List<UserDto> allUsers(Integer pageNumber, Integer pageSize);
}
