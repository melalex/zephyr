package com.zephyr.auth.service;

import com.zephyr.auth.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {

    Mono<UserDto> createCustomer(UserDto dto);
}
