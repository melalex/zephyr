package com.zephyr.auth.service.impl;

import com.zephyr.auth.domain.User;
import com.zephyr.auth.domain.UserToken;
import com.zephyr.auth.exceptions.UserAlreadyExistsException;
import com.zephyr.auth.repository.UserRepository;
import com.zephyr.auth.service.UserService;
import com.zephyr.auth.support.UserTokenFactory;
import com.zephyr.data.protocol.dto.CreateUserDto;
import com.zephyr.data.protocol.dto.LoginUserDto;
import com.zephyr.data.protocol.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserTokenFactory userTokenFactory;

    @Override
    public UserDto extract(Principal principal) {
        return new UserDto(principal.getName());
    }

    @Override
    public UserDto create(CreateUserDto user) {
        var username = user.getUsername();

        if (userRepository.exists(User.of(username).asExample())) {
            throw new UserAlreadyExistsException("User with username [ %s ] already exists", username);
        }

        var entity = modelMapper.map(user, User.class);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));

        var saved = userRepository.save(entity);

        log.info("New user [ {} ] created", username);

        return modelMapper.map(saved, UserDto.class);
    }

    @Override
    public UserToken login(LoginUserDto loginUserDto) {
        User user = userRepository.findByUsername(loginUserDto.getUsername())
                .filter(u -> passwordEncoder.matches(loginUserDto.getPassword(), u.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("Wrong username or password"));

        return userTokenFactory.create(user);
    }
}
