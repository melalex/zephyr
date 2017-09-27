package com.zephyr.auth.service;

import com.zephyr.auth.repository.UserRepository;
import com.zephyr.auth.service.impl.UserServiceImpl;
import com.zephyr.mapping.mappers.ExtendedMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExtendedMapper extendedMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldLoad() {

    }

    @Test
    public void shouldThrowWhenLoad() {

    }

    @Test
    public void shouldCreate() {

    }

    @Test
    public void shouldThrowWhileCreate() {

    }
}