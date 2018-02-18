package com.zephyr.auth.service.impl;

import com.zephyr.auth.domain.User;
import com.zephyr.auth.service.PrincipalService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PrincipalServiceImpl implements PrincipalService {

    @Override
    public Principal extract(Principal principal) {
        return new User(principal.getName());
    }
}
