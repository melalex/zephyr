package com.zephyr.auth.controllers;

import com.zephyr.auth.service.UserService;
import com.zephyr.data.protocol.dto.CreateUserDto;
import com.zephyr.data.protocol.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    @GetMapping({"/current", "/me"})
    public UserDto current(Principal principal) {
        return userService.extract(principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid CreateUserDto user) {
        return userService.create(user);
    }
}
