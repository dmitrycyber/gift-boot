package com.epam.esm.service.impl;

import com.epam.esm.jpa.OrderRepository;
import com.epam.esm.jpa.UserRepository;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.service.UserService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        UserEntity userEntity = EntityConverter.convertUserDtoToEntity(userDto);

        UserEntity savedUser = userRepository.register(userEntity);

        return EntityConverter.convertUserEntityToDto(savedUser);
    }

    @Override
    public UserDto userProfile(Long userId) {
        UserEntity userEntity = userRepository.findById(userId);
        return EntityConverter.convertUserEntityToDto(userEntity);
    }

    @Override
    public List<UserDto> findAll(Integer pageNumber, Integer pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll(pageNumber, pageSize);
        return userEntityList.stream()
                .map(EntityConverter::convertUserEntityToDto)
                .collect(Collectors.toList());
    }
}
