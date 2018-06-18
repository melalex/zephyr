package com.zephyr.commons.support;

import lombok.Value;

import java.security.Principal;

@Value
public class OAuth2Principal {

    private String name;

    public Principal asPrincipal() {
        return () -> name;
    }
}
