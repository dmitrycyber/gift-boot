package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto register(@RequestBody @Valid @Validated(CreatingDto.class) UserDto userDto) {
        return userService.register(userDto);
    }

    @Override
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto userProfile(@PathVariable Long id) {
        return userService.userProfile(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> allUsers(Integer pageNumber, Integer pageSize) {
        return userService.findAll(pageNumber, pageSize);
    }
}
