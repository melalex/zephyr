package com.zephyr.mapping.support.impl;

import com.zephyr.mapping.domain.User;
import com.zephyr.mapping.dto.UserDto;
import com.zephyr.mapping.support.test.UserConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.Converter;
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
@Import(ConverterSupportTest.TestConfiguration.class)
public class ConverterSupportTest {
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
    public void shouldConvert() {
        User user = new User(FULL_NAME);

        UserDto result = modelMapper.map(user, UserDto.class);

        assertEquals(FIRST_NAME, result.getFirstName());
        assertEquals(LAST_NAME, result.getLastName());
    }

    @Test
    public void shouldReverseConvert() {
        UserDto userDto = new UserDto(FIRST_NAME, LAST_NAME);

        User result = modelMapper.map(userDto, User.class);

        assertEquals(FULL_NAME, result.getName());

    }

    @Configuration
    @EnableAutoConfiguration
    public static class TestConfiguration {

        @Bean
        public Converter<User, UserDto> userDtoConverter() {
            return new UserConverter();
        }
    }
}