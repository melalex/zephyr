package com.zephyr.mapping.decorators;

import com.zephyr.mapping.domain.User;
import com.zephyr.mapping.dto.UserDto;
import com.zephyr.mapping.support.test.UserConverter;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ToSetConverterTest {
    private static final String FULL_NAME = "Vasya Pupkin";
    private static final String FIRST_NAME = "Vasya";
    private static final String LAST_NAME = "Pupkin";

    private ToSetConverter<User, UserDto> testInstance = ToSetConverter.of(new UserConverter());

    @Test
    public void shouldConvert() {
        Set<UserDto> result = testInstance.convert(userCollection());

        assertEquals(userDtoCollection(), result);
    }

    @Test
    public void shouldReverseConvert() {
        Set<User> result = ((Set<User>) testInstance.getReverse().convert(userDtoCollection()));

        assertEquals(userCollection(), result);
    }

    private Set<User> userCollection() {
        return Set.of(new User(FULL_NAME));
    }

    private Set<UserDto> userDtoCollection() {
        return Set.of(new UserDto(FIRST_NAME, LAST_NAME));
    }
}