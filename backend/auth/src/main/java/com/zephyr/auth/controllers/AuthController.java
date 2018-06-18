package com.zephyr.auth.controllers;

import com.zephyr.auth.domain.UserToken;
import com.zephyr.auth.service.UserService;
import com.zephyr.data.protocol.dto.LoginUserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class AuthController {

    private UserService userService;

    @PostMapping
    public UserToken login(@RequestBody @Valid LoginUserDto loginUserDto) {
        return userService.login(loginUserDto);
    }
}
