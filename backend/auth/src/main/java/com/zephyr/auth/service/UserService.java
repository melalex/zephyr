package com.zephyr.auth.service;

import com.zephyr.auth.domain.UserToken;
import com.zephyr.data.protocol.dto.CreateUserDto;
import com.zephyr.data.protocol.dto.LoginUserDto;
import com.zephyr.data.protocol.dto.UserDto;

import java.security.Principal;

public interface UserService {

    UserDto extract(Principal principal);

    UserDto create(CreateUserDto user);

    UserToken login(LoginUserDto loginUserDto);
}
