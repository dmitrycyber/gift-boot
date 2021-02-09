package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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
        UserDto savedUser = userService.register(userDto);
        addSelfLinks(savedUser);
        return savedUser;
    }

    @Override
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto userProfile(@PathVariable Long id) {
        UserDto userDto = userService.userProfile(id);
        addSelfLinks(userDto);
        return userDto;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> allUsers(Integer pageNumber, Integer pageSize) {
        List<UserDto> userDtoList = userService.findAll(pageNumber, pageSize);
        addSelfLinksToList(userDtoList);
        return userDtoList;
    }

    private void addSelfLinksToList(List<UserDto> userDtos) {
        for (UserDto userDto : userDtos) {
            addSelfLinks(userDto);
        }
    }

    private void addSelfLinks(UserDto userDto) {
        @Valid Set<OrderDto> orders = userDto.getOrders();
        for (OrderDto orderDto : orders) {
            orderDto.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(OrderControllerImpl.class)
                            .orderById(orderDto.getId()))
                    .withSelfRel());
        }
        userDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UserControllerImpl.class)
                        .userProfile(userDto.getId()))
                .withSelfRel());
    }
}
