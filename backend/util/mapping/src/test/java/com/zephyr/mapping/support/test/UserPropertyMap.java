package com.zephyr.mapping.support.test;

import com.zephyr.mapping.domain.User;
import com.zephyr.mapping.dto.UserDto;
import com.zephyr.mapping.support.impl.PropertyMapSupport;
import org.modelmapper.PropertyMap;

public class UserPropertyMap extends PropertyMapSupport<User, UserDto> {

    @Override
    protected PropertyMap<User, UserDto> mapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                map().setFirstName(source.getName());
                map().setLastName(source.getName());
            }
        };
    }

    @Override
    protected PropertyMap<UserDto, User> reverseMapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                map().setName(source.getFirstName());
            }
        };
    }
}
