package com.zephyr.auth.maps;

import com.zephyr.auth.domain.User;
import com.zephyr.auth.dto.UserDto;
import com.zephyr.mapping.decorators.ToSetConverter;
import com.zephyr.mapping.support.impl.ConverterSupport;
import com.zephyr.mapping.support.impl.PropertyMapSupport;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserPropertyMap extends PropertyMapSupport<User, UserDto> {
    private ToSetConverter<User.Role, GrantedAuthority> authorityConverter;

    @Autowired
    public void setAuthorityConverter(ConverterSupport<User.Role, GrantedAuthority> authorityConverter) {
        this.authorityConverter = ToSetConverter.of(authorityConverter);
    }

    @Override
    protected PropertyMap<User, UserDto> mapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                using(authorityConverter)
                        .map(source.getRoles())
                        .setAuthorities(null);
            }
        };
    }

    @Override
    protected PropertyMap<UserDto, User> reverseMapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                using(authorityConverter.getReverse())
                        .map(source.getAuthorities())
                        .setRoles(null);
            }
        };
    }
}
