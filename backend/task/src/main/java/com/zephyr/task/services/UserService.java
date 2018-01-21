package com.zephyr.task.services;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<String> getCurrentUserId();
}
