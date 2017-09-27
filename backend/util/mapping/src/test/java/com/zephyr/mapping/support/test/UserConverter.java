package com.zephyr.mapping.support.test;

import com.google.common.base.Joiner;
import com.zephyr.mapping.domain.User;
import com.zephyr.mapping.dto.UserDto;
import com.zephyr.mapping.support.impl.ConverterSupport;

public class UserConverter extends ConverterSupport<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        String[] parts = source.getName().split(" ");

        return new UserDto(parts[0], parts[1]);
    }

    @Override
    protected User reverseConvert(UserDto destination) {
        return new User(Joiner.on(" ").join(destination.getFirstName(), destination.getLastName()));
    }

}
