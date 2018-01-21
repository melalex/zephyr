package com.zephyr.task.services.impl;

import com.zephyr.errors.utils.ExceptionUtils;
import com.zephyr.task.services.UserService;
import com.zephyr.task.integration.clients.AuthServiceClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {

    @Setter(onMethod = @__(@Autowired))
    private AuthServiceClient authServiceClient;

    @Override
    public Mono<String> getCurrentUserId() {
        return authServiceClient.current()
                .switchIfEmpty(ExceptionUtils.noCurrentUserAsync())
                .map(Principal::getName);
    }
}
