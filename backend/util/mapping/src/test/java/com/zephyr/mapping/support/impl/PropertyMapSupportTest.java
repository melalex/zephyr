package com.zephyr.mapping.support.impl;

import com.zephyr.mapping.domain.User;
import com.zephyr.mapping.dto.UserDto;
import com.zephyr.mapping.support.test.UserPropertyMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(PropertyMapSupportTest.TestConfiguration.class)
public class PropertyMapSupportTest {
    private static final String FULL_NAME = "Vasya Pupkin";
    private static final String FIRST_NAME = "Vasya";
    private static final String LAST_NAME = "Pupkin";

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void shouldInjectModelMapper() {
        assertNotNull(modelMapper);
    }

    @Test
    public void shouldMap() {
        User user = new User(FULL_NAME);

        UserDto result = modelMapper.map(user, UserDto.class);

        assertEquals(FULL_NAME, result.getFirstName());
        assertEquals(FULL_NAME, result.getLastName());
    }

    @Test
    public void shouldReverseMap() {
        UserDto userDto = new UserDto(FIRST_NAME, LAST_NAME);

        User result = modelMapper.map(userDto, User.class);

        assertEquals(FIRST_NAME, result.getName());

    }

    @Configuration
    @EnableAutoConfiguration
    public static class TestConfiguration {

        @Bean
        public PropertyMapSupport<User, UserDto> userDtoPropertyMap() {
            return new UserPropertyMap();
        }
    }
}