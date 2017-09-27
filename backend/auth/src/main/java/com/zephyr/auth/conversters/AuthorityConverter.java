package com.zephyr.auth.conversters;

import com.zephyr.auth.domain.User;
import com.zephyr.mapping.support.impl.ConverterSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthorityConverter extends ConverterSupport<User.Role, GrantedAuthority> {

    @Override
    public GrantedAuthority convert(User.Role source) {
        return new SimpleGrantedAuthority(source.name());
    }

    @Override
    protected User.Role reverseConvert(GrantedAuthority source) {
        return User.Role.valueOf(source.getAuthority());
    }
}