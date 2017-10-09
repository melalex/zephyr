package com.zephyr.auth.service.impl;

import com.zephyr.auth.domain.User;
import com.zephyr.auth.service.dto.UserDto;
import com.zephyr.auth.repository.UserRepository;
import com.zephyr.auth.service.UserService;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.zephyr.commons.LoggingUtils.info;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_CREATION_MESSAGE = "Create User: {}";

    @Setter(onMethod = @__(@Autowired))
    private UserRepository userRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        return userRepository
                .findById(username)
                .switchIfEmpty(throwUserNotExists(username))
                .map(mapper.mapperFor(UserDto.class))
                .block();
    }

    @Override
    public Mono<UserDto> createCustomer(@NonNull UserDto dto) {
        return userRepository
                .existsById(dto.getUsername())
                .map(e -> !e
                        ? saveUser(dto)
                        : throwUserAlreadyExists()
                )
                .doOnNext(info(log, USER_CREATION_MESSAGE))
                .map(mapper.mapperFor(UserDto.class));
    }

    private Mono<User> saveUser(UserDto dto) {
        return userRepository.save(toCustomer(dto));
    }

    private Mono<User> throwUserNotExists(String username) {
        return Mono.error(new UsernameNotFoundException(username));
    }

    private Mono<User> throwUserAlreadyExists() {
        // TODO: Change exception type
        return Mono.error(new RuntimeException());
    }

    private User toCustomer(UserDto dto) {
        User customer = toUser(dto);
        customer.setRoles(Set.of(User.Role.CUSTOMER));

        return customer;
    }

    private User toUser(UserDto dto) {
        User user = mapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setEmailConfirmed(false);

        return user;
    }
}